package com.wcecil.webservice.util;

import java.util.ArrayList;
import java.util.List;

import com.wcecil.beans.GameState;
import com.wcecil.beans.gameobjects.Card;
import com.wcecil.beans.gameobjects.CardTemplate;
import com.wcecil.beans.gameobjects.Player;

public class MaskingHelper {
	public static List<Card> maskCards(GameState g) {
		List<Card> mainDeck = g.getMainDeck();
		return maskCards(mainDeck);
	}

	public static List<Card> maskCards(List<Card> mainDeck) {
		List<Card> deck = new ArrayList<>();

		for (@SuppressWarnings("unused")
		Card ignored : mainDeck) {
			deck.add(new CardTemplate());
		}
		return deck;
	}

	public static List<Player> maskPlayersDetails(GameState g, String userId) {
		List<Player> players = new ArrayList<>();

		for (Player player : g.getPlayers()) {
			players.add(maskPlayerDetails(player, userId));
		}

		return players;
	}

	public static Player maskPlayerDetails(Player p, String userId) {
		Player p2 = new Player();

		if (p != null) {
			p2.setId(p.getId());
			p2.setMoney(p.getMoney());

			p2.setDeck(maskCards(p.getDeck()));
			p2.setDiscard(p.getDiscard());
			p2.setInplay(p.getInplay());
			p2.setPlayed(p.getPlayed());

			// TODO MASK HAND OF NOT YOU
			p2.setHand(p.getHand());
		}
		return p2;
	}
}
