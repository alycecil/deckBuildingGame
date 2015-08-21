package com.wcecil.data.repositiories;

import static org.springframework.data.mongodb.core.query.Criteria.*
import static org.springframework.data.mongodb.core.query.Query.*
import groovy.transform.CompileStatic;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

import com.wcecil.beans.GamePlayerState
import com.wcecil.beans.GameState
import com.wcecil.data.objects.User

@Service
@CompileStatic
public class GameRepository {
	private final static String __REPO = "deck.game.full";
	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	AuditRepository auditRepo;

	@Autowired
	UsersRepository usersRepo;

	public List<GameState> getUsersGames(String gameId){
	}

	public GameState getGame(String gameId, boolean includeAudit){
		GameState game = mongoTemplate.findById(gameId, GameState.class, __REPO);

		return game
	}

	public GameState save(GameState g){
		if(g){
			auditRepo.flushAudits(g);

			mongoTemplate.save(g, __REPO);
		}

		return g
	}

	public GamePlayerState loadGamesForUser(String userId) {
		GamePlayerState state = new GamePlayerState();
		User user = usersRepo.getUser(userId)
		state.playerName = user.name
		state.id = user.id
		state.games = []

		user.games?.each{
			state.games << getGame(it, false)
		}
		
		return state
	}
}
