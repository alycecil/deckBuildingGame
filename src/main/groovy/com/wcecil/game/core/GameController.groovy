

package com.wcecil.game.core

import groovy.transform.Synchronized

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.wcecil.beans.dto.GameAudit;
import com.wcecil.beans.dto.GameSearch;
import com.wcecil.beans.dto.GameState;
import com.wcecil.beans.dto.User;
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.settings.Settings
import com.wcecil.data.repositiories.GameRepository
import com.wcecil.data.repositiories.GameSearchRepository
import com.wcecil.data.repositiories.UsersRepository
import com.wcecil.game.actions.Action
import com.wcecil.game.actions.initial.LoadGame
import com.wcecil.game.rules.Rule
import com.wcecil.game.triggers.Trigger
import com.wcecil.websocket.messanger.MessangerService

@Service
class GameController {
	private @Autowired GameRepository gamesRepo;
	private @Autowired UsersRepository usersRepo;
	private @Autowired GameSearchRepository gameSearchRepo;
	private @Autowired MessangerService messangerService;

	public GameState createBaseGame(){
		GameState g = new GameState();
		doAction(g, new LoadGame());

		gamesRepo.save(g);

		g
	}

	@Synchronized
	public void matchGames(){
		//TODO move to redis

		//TODO use RLock

		List<GameSearch> list = gameSearchRepo.listAll()

		int k = list.size();
		while(list.size()>1){
			//"timeout"
			if(k<0){
				break;
			}
			k--;

			GameSearch a, b;
			a = gameSearchRepo.popPlayer(list);
			b = gameSearchRepo.popPlayer(list);

			if(!a){
				println 'Unable to find even 1'
			}else if(a&&!b){
				gameSearchRepo.save(a)
				println "re-adding a of $a"
			}else if(a.userId?.equals(b.userId)){ //TODO SOLVED WHEN USING RSET IN FUTURE
				gameSearchRepo.save(a)
				println "Found dupe for ${a.userId} and ${b.userId}"
			}else{
				println "Matched a game for ${a.userId} and ${b.userId}"
				try{
					def g = createBaseGame()
					addUsersToGame([a.userId, b.userId], g.id)
				}catch(e){
					println "Failed to create game";
					e.printStackTrace()
					gameSearchRepo.save(a)
					gameSearchRepo.save(b)
				}
			}

			list = gameSearchRepo.listAll()
		}
	}

	public boolean addUsersToGame(List<String> userIds, String gameId){
		userIds.each{ userId ->
			addUserToGame(userId, gameId)
		}
	}

	public User addUserToGame(String userId, String gameId){
		GameState game = gamesRepo.getGame(gameId, false);

		boolean matched = false
		Player match = null;
		for(Player p : game.players){
			//if is available assign
			if(p.getUserId()==null){
				p.setUserId(userId)
				match = p
				matched = true
				break;
			}
		}

		if(!matched){
			throw new IllegalStateException("Game is full!");
		}

		

		def u = usersRepo.addGameToUser(userId, gameId);
		
		println "Added $gameId to $u"
		game.audit << new GameAudit(gameId: gameId, playerId: match.id, order: 0, message: "${u.id} joined the game")
		
		gamesRepo.save(game);

		messangerService.alertUserNewGame(u.id, gameId)

		u
	}

	def doAction(GameState g, Action a){
		def result = null
		if(a.isValid(g)){

			doTriggers( g,  a )

			result = a.doAction(g, this);

			def tic = g.ticCount.getAndIncrement()
			saveAudit(g, a)

			g.getRules().each{ Rule r ->
				r.doRule(g, this)
			}
			
			if(messangerService)
				a.sendNotification(g, messangerService)
		}else{
			throw new IllegalStateException("Game in illegal state for attempting $a on $g");
		}

		result
	}

	static void doTriggers(GameState g, Action a){
		g.triggers.each { Trigger t ->
			if(t.isTriggered(a)){
				t.doTrigger(g, a)
			}
		}
	}

	static void saveAudit(GameState g, Action a) {
		def tic = g.ticCount.get()
		if(a.audit){
			if(Settings.debug) println "$tic:${a.audit}"

			GameAudit ga = new GameAudit(gameId: g.getId(), playerId : a.getSourcePlayer()?.getUserId(), order: tic, message:a.audit )
			g.audit << ga
		}
	}

	public static Action buildAction(GameState g, String action) {
		Action a = null;

		a = new GameController().getClass().getClassLoader().loadClass("com.wcecil.game.actions.core.$action", true)?.newInstance()

		if(a==null){
			throw new IllegalStateException("Cannot Parse Action '$action'")
		}

		a.setSourcePlayer(g.getCurrentPlayer())

		return a;
	}
}
