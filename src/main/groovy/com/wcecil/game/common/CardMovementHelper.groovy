package com.wcecil.game.common

import com.wcecil.game.actions.core.ShuffleDiscardIntoDeck
import com.wcecil.beans.GameState;
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.game.core.GameController
import com.wcecil.game.actions.Action;

class CardMovementHelper {
	static Card drawCard(Player p, GameState g, Action cause){
		Card result = null
		if(!p.deck){
			def shuffle = new ShuffleDiscardIntoDeck(targetPlayer:p, cause:cause);
			GameController.doAction(g, shuffle)
		}

		if(p.deck){
			result = p.deck.remove(0)
			p.hand << result
		}

		result
	}


	static boolean moveFullZones(List<Card> fromZone, List<Card> toZone, GameState g, Action cause) {
		if(fromZone){
			toZone.addAll(fromZone)
			fromZone.clear()
			return true
		}
		false
	}

	static boolean moveDiscardToDeckAndShuffle(Player p, GameState g, Action cause) {
		if(p.discard){
			p.deck.addAll(p.discard)
			p.discard.clear()
			Collections.shuffle(p.deck)
			return true
		}
		false
	}

	static void playCard(Player p, Card card) {
		if(p.hand.contains(card)){
			p.hand.remove(card)
			p.played.add(card)
		}
	}

	static boolean buyCard(GameState g, Player p, Card card) {
		boolean bought = false
		if(g.available.contains(card)){
			g.available.remove(card)
			p.discard.add(card)
			bought = true
		}

		g.staticCards.each {
			if(it.contains(card)){
				it.remove(card)
				p.discard.add(card)
				bought = true
			}
		}

		bought
	}
}
