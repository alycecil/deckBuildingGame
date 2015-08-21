package com.wcecil.webservice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.wcecil.common.settings.ApplicationComponent;
import com.wcecil.data.objects.GameAudit;
import com.wcecil.data.repositiories.AuditRepository;
import com.wcecil.data.repositiories.GameRepository;
import com.wcecil.data.repositiories.GameSearchRepository;
import com.wcecil.data.repositiories.UsersRepository;
import com.wcecil.game.actions.Action;
import com.wcecil.game.core.GameController;
import com.wcecil.webservice.service.AuthenticationService;
import com.wcecil.webservice.util.MaskingHelper;

@RestController
@RequestMapping("/game")
public class GameWebController {
	private @Autowired GameRepository gamesRepo;

	private @Autowired AuditRepository auditRepo;

	private @Autowired AuthenticationService authService;

	private @Autowired UsersRepository usersRepo;

	private @Autowired GameSearchRepository gameSearchRepo;

	private @Autowired ApplicationComponent context;
	
	private @Autowired GameController gameController;

	public GameWebController() {
		System.out.print("Creating " + this);
	}

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public GamePlayerState listGames(
			@RequestParam(value = "token") String token,
			HttpServletResponse response) {
		String userId = authService.getUserFromToken(token, response);
		GamePlayerState state = gamesRepo.loadGamesForUser(userId);

		return state;
	}

	@RequestMapping(value = "/get", method = { RequestMethod.GET })
	public GameState getGame(@RequestParam(value = "id") String gameId,
			@RequestParam(value = "token") String token,
			HttpServletResponse response) {
		String userId = authService.getUserFromToken(token, response);

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
			retVal.setAuditCount(auditRepo.getCount(gameId, userId));
		}

		return retVal;
	}

	@RequestMapping(value = "/audit", method = { RequestMethod.GET })
	public List<GameAudit> getGameAudits(
			@RequestParam(value = "id") String gameId,
			@RequestParam(value = "token") String token,
			HttpServletResponse response) {
		authService.getUserFromToken(token, response);
		return auditRepo.getAuditsForGame(gameId);
	}

	@RequestMapping(value = "/queue", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Boolean queueGame(@RequestParam(value = "token") String token,
			HttpServletResponse response) {
		String userId = authService.getUserFromToken(token, response);

		gameSearchRepo.enterSearch(userId);

		return true;
	}

	@RequestMapping(value = "/queueDebug", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Boolean queueDebugGame(@RequestParam(value = "token") String token,
			@RequestParam(value = "userId") String _userId,
			HttpServletResponse response) throws IOException {
		if (!context.getAllowDebug()) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Not Authorized");
			return null;
		}
		String userId = authService.getUserFromToken(token, response);
		
		gameSearchRepo.enterSearch(userId);		
		gameSearchRepo.enterSearch(_userId);

		return true;
	}

	@RequestMapping(value = "/new", method = { RequestMethod.GET,
			RequestMethod.POST })
	public GameState newGame(@RequestParam(value = "token") String token,
			HttpServletResponse response) {
		String userId = authService.getUserFromToken(token, response);

		GameState g = gameController.createBaseGame();

		gameController.addUserToGame(userId, g.getId());

		return getGame(g.getId(), token, response);
	}

	@RequestMapping(value = "/move", method = { RequestMethod.GET,
			RequestMethod.POST })
	public GameState move(
			@RequestParam(value = "id") String gameId,
			@RequestParam(value = "action") String action,
			@RequestParam(value = "sourceCard", required = false) Long sourceCard,
			@RequestParam(value = "targetCard", required = false) Long targetCard,
			@RequestParam(value = "targetPlayer", required = false) Long targetPlayer,
			@RequestParam(value = "token") String token,
			HttpServletResponse response) {
		// TODO String userId =
		authService.getUserFromToken(token, response);

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

		return getGame(gameId, token, response);
	}
}
