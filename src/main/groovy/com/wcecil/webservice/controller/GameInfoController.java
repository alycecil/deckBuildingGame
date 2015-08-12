package com.wcecil.webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wcecil.beans.GamePlayerState;
import com.wcecil.beans.GameState;
import com.wcecil.beans.gameobjects.ActualCard;
import com.wcecil.beans.gameobjects.Card;
import com.wcecil.beans.gameobjects.Player;
import com.wcecil.data.repositiories.GameRepository;
import com.wcecil.data.repositiories.UsersRepository;
import com.wcecil.game.actions.Action;
import com.wcecil.game.actions.initial.LoadGame;
import com.wcecil.game.core.GameController;
import com.wcecil.webservice.service.AuthenticationService;
import com.wcecil.webservice.util.MaskingHelper;

@RestController
@RequestMapping("/game")
public class GameInfoController {
	@Autowired
	GameRepository gamesRepo;

	@Autowired
	AuthenticationService authService;

	@Autowired
	UsersRepository usersRepo;

	public GameInfoController() {
		System.out.print("Creating " + this);
	}

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public GamePlayerState listGames(@RequestParam(value = "token") String token) {
		String userId = authService.getUserFromToken(token);
		GamePlayerState state = gamesRepo.loadGamesForUser(userId);

		return state;
	}

	@RequestMapping(value = "/get", method = { RequestMethod.GET })
	public GameState getGame(@RequestParam(value = "id") String gameId,
			@RequestParam(value = "token") String token) {
		String userId = authService.getUserFromToken(token);

		GameState g = gamesRepo.getGame(gameId, true);

		GameState retVal = new GameState();

		if (g != null) {
			retVal.setId(gameId);
			// retVal.setAudit(g.getAudit());
			retVal.setCurrentPlayer(MaskingHelper.maskPlayerDetails(
					g.getCurrentPlayer(), userId));
			retVal.setPlayers(MaskingHelper.maskPlayersDetails(g, userId));
			retVal.setTicCount(g.getTicCount());
			retVal.setMainDeck(MaskingHelper.maskCards(g));
			retVal.setStaticCards(g.getStaticCards());
			retVal.setTriggers(g.getTriggers());
			retVal.setAvailable(g.getAvailable());
		}

		return g;
	}

	@RequestMapping(value = "/new", method = { RequestMethod.GET,
			RequestMethod.POST })
	public GameState newGame(@RequestParam(value = "token") String token) {
		String userId = authService.getUserFromToken(token);

		GameState g = new GameState();
		GameController.doAction(g, new LoadGame());

		gamesRepo.save(g);

		usersRepo.addGameToUser(userId, g.getId());

		return getGame(g.getId(), token);
	}

	@RequestMapping(value = "/move", method = { RequestMethod.GET,
			RequestMethod.POST })
	public GameState move(
			@RequestParam(value = "id") String gameId,
			@RequestParam(value = "action") String action,
			@RequestParam(value = "sourceCard", required = false) Long sourceCard,
			@RequestParam(value = "targetCard", required = false) Long targetCard,
			@RequestParam(value = "targetPlayer", required = false) Long targetPlayer,
			@RequestParam(value = "token") String token) {
		// TODO String userId =
		authService.getUserFromToken(token);

		GameState g = gamesRepo.getGame(gameId, true);

		Action a = GameController.buildAction(g, action);

		if (targetPlayer != null) {
			for (Player p : g.getPlayers()) {
				if (p.getId().equals(targetPlayer)) {
					a.setTargetPlayer(p);
					break;
				}
			}

			if (a.getTargetPlayer() == null) {
				throw new IllegalStateException("Unable to find target player");
			}
		}

		if (sourceCard != null) {
			for (Card c : a.getSourcePlayer().getHand()) {
				if (c instanceof ActualCard) {
					ActualCard c2 = (ActualCard) c;
					if (sourceCard.equals(c2.getId())) {
						a.setSourceCard(c2);
						break;
					}
				}
			}

			if (a.getSourceCard() == null) {
				throw new IllegalStateException("Unable to find source card");
			}
		}

		if (targetCard != null) {
			for (Card c : g.getAllCards()) {
				if (c instanceof ActualCard) {
					ActualCard c2 = (ActualCard) c;
					if (targetCard.equals(c2.getId())) {
						a.setTargetCard(c2);
						break;
					}
				}
			}

			if (a.getTargetCard() == null) {
				throw new IllegalStateException("Unable to find target card");
			}
		}

		GameController.doAction(g, a);

		gamesRepo.save(g);

		return getGame(gameId, token);
	}
}
