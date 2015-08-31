package com.wcecil.io.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wcecil.beans.dto.GameState;
import com.wcecil.beans.dto.User;
import com.wcecil.beans.gameobjects.Card;
import com.wcecil.beans.gameobjects.CardTemplate;
import com.wcecil.beans.gameobjects.Player;
import com.wcecil.data.repositiories.AuditRepository;
import com.wcecil.data.repositiories.UsersRepository;

@Service
public class MaskingHelper {
	@Autowired
	UsersRepository usersRepo;
	private @Autowired AuditRepository auditRepo;

	public GameState maskGame(String gameId, String userId, GameState g) {
		GameState retVal = new GameState();

		if (g != null) {
			retVal.setId(gameId);
			retVal.setName(g.getName());
			// retVal.setAudit(g.getAudit());
			retVal.setCurrentPlayer(maskPlayerDetails(g.getCurrentPlayer(),
					userId));
			retVal.setPlayers(maskPlayersDetails(g, userId));
			retVal.setTicCount(g.getTicCount());
			retVal.setMainDeck(maskCards(g));
			retVal.setStaticCards(g.getStaticCards());
			retVal.setTriggers(g.getTriggers());
			retVal.setAvailable(g.getAvailable());
			retVal.setAuditCount(auditRepo.getCount(gameId, userId));
		}
		return retVal;
	}

	public List<Card> maskCards(GameState g) {
		List<Card> mainDeck = g.getMainDeck();
		return maskCards(mainDeck);
	}

	public List<Card> maskCards(List<Card> cards) {
		List<Card> deck = new ArrayList<>();

		for (@SuppressWarnings("unused")
		Card ignored : cards) {
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

		if (p != null) {
			Player p2 = new Player();

			p2.setUserId(p.getUserId());
			p2.setUser(p.getUser());
			
			getUserDetailsMasked(p2);

			p2.setId(p.getId());

			p2.setMoney(p.getMoney());

			p2.setDeck(maskCards(p.getDeck()));
			p2.setDiscard(p.getDiscard());
			p2.setInplay(p.getInplay());
			p2.setPlayed(p.getPlayed());

			if (p.getUserId() == null
					|| (userId != null && userId.equals(p.getUserId()))) {
				p2.setHand(p.getHand());
			} else {
				p2.setHand(maskCards(p.getHand()));
			}
			
			
			return p2;
		}

		return null;
	}

	private void getUserDetailsMasked(Player p2) {
		if (p2.getUser() == null && p2.getUserId() != null) {
			User user = usersRepo.getUser(p2.getUserId());
			if (user != null) {
				user.setGames(null);
				user.setPassword(null);
				if (user.getName() == null) {
					if (user.getLogin() == null
							|| user.getLogin().isEmpty()) {
						user.setLogin("[???]");
					}
					user.setName(user.getLogin());
				}
				p2.setUser(user);
			}
		}
	}
}
