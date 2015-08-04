package com.wcecil.webservice.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wcecil.actions.Action;
import com.wcecil.actions.initial.LoadGame;
import com.wcecil.beans.GamePlayerState;
import com.wcecil.beans.GameState;
import com.wcecil.beans.gameobjects.ActualCard;
import com.wcecil.beans.gameobjects.Card;
import com.wcecil.beans.gameobjects.CardTemplate;
import com.wcecil.beans.gameobjects.Player;
import com.wcecil.core.GameController;

@RestController
@RequestMapping("/game")
public class GameInfoController {

	//TODO pin to real data
	Map<Long, GameState> games = new Hashtable<>();

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public GamePlayerState listGames() {
		//TODO pin to real data
		GamePlayerState state = new GamePlayerState();
		
		state.setGames(new ArrayList<>(games.values()));
		state.setPlayerName("YOU");
		state.setId(1l);
		
		return state;
	}
	@RequestMapping(value = "/get", method = { RequestMethod.GET })
	public GameState getGame(@RequestParam(value = "id") Long id) {

		GameState g = games.get(id);

		GameState retVal = new GameState(false);

		retVal.setId(id);
		retVal.setAudit(g.getAudit());
		retVal.setCurrentPlayer(maskPlayerDetails(g.getCurrentPlayer()));
		retVal.setPlayers(maskPlayersDetails(g));
		retVal.setTicCount(g.getTicCount());
		retVal.setMainDeck(maskCards(g));
		retVal.setStaticCards(g.getStaticCards());
		retVal.setTriggers(g.getTriggers());
		retVal.setAvailable(g.getAvailable());

		return g;
	}

	private List<Card> maskCards(GameState g) {
		List<Card> mainDeck = g.getMainDeck();
		return maskCards(mainDeck);
	}

	private List<Card> maskCards(List<Card> mainDeck) {
		List<Card> deck = new ArrayList<>();

		for (@SuppressWarnings("unused")
		Card ignored : mainDeck) {
			deck.add(new CardTemplate());
		}
		return deck;
	}

	private List<Player> maskPlayersDetails(GameState g) {
		List<Player> players = new ArrayList<>();

		for (Player player : g.getPlayers()) {
			players.add(maskPlayerDetails(player));
		}

		return players;
	}

	private Player maskPlayerDetails(Player p) {
		Player p2 = new Player();

		p2.setId(p.getId());
		p2.setMoney(p.getMoney());

		p2.setDeck(maskCards(p.getDeck()));
		p2.setDiscard(p.getDiscard());
		p2.setInplay(p.getInplay());
		p2.setPlayed(p.getPlayed());
		
		//TODO MASK HAND OF NOT YOU
		p2.setHand(p.getHand());

		return p2;
	}

	@RequestMapping(value = "/new", method = { RequestMethod.GET,
			RequestMethod.POST })
	public GameState newGame() {

		GameState g = new GameState();
		GameController.doAction(g, new LoadGame());

		games.put(g.getId(), g);

		return getGame(g.getId());
	}

	@RequestMapping(value = "/move", method = { RequestMethod.GET,
			RequestMethod.POST })
	public GameState move(@RequestParam(value = "id") Long id,
			@RequestParam(value = "action") String action,
			@RequestParam(value = "card", required = false) Long card,
			@RequestParam(value = "target", required = false) Long target) {

		GameState g = games.get(id);

		Action a = GameController.buildAction(g, action);
		if (target != null) {
			for (Player p : g.getPlayers()) {
				if (p.getId().equals(target)) {
					a.setTargetPlayer(p);
				}
			}
		}

		if (card != null) {
			for (Card c : a.getSourcePlayer().getHand()) {
				if (c instanceof ActualCard) {
					ActualCard c2 = (ActualCard) c;
					if (card.equals(c2.getId())) {
						a.setSourceCard(c2);
					}
				}
			}
		}

		GameController.doAction(g, a);

		return getGame(id);
	}
}
