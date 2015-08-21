package com.wcecil.webservice.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wcecil.beans.GameState;
import com.wcecil.beans.gameobjects.Card;
import com.wcecil.beans.gameobjects.CardTemplate;
import com.wcecil.beans.gameobjects.Player;
import com.wcecil.data.repositiories.UsersRepository;

@Service
public class MaskingHelper {
	@Autowired UsersRepository usersRepo;
	
	public List<Card> maskCards(GameState g) {
		List<Card> mainDeck = g.getMainDeck();
		return maskCards(mainDeck);
	}

	public List<Card> maskCards(List<Card> mainDeck) {
		List<Card> deck = new ArrayList<>();

		for (@SuppressWarnings("unused")
		Card ignored : mainDeck) {
			deck.add(new CardTemplate());
		}
		return deck;
	}

	public List<Player> maskPlayersDetails(GameState g, String userId) {
		List<Player> players = new ArrayList<>();

		for (Player player : g.getPlayers()) {
			players.add(maskPlayerDetails(player, userId));
		}

		return players;
	}

	public Player maskPlayerDetails(Player p, String userId) {
		Player p2 = new Player();

		if (p != null) {
			p2.setUserId(p.getUserId());
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
