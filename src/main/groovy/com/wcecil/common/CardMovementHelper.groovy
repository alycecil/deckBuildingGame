package com.wcecil.common

import com.wcecil.actions.AbstractAction;
import com.wcecil.actions.core.ShuffleDiscardIntoDeck
import com.wcecil.beans.GameState;
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.core.GameController

class CardMovementHelper {
	static Card drawCard(Player p, GameState g, AbstractAction cause){
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


	static boolean moveFullZones(List<Card> fromZone, List<Card> toZone, GameState g, AbstractAction cause) {
		if(fromZone){
			toZone.addAll(fromZone)
			fromZone.clear()
			return true
		}
		false
	}

	static boolean moveDiscardToDeckAndShuffle(Player p, GameState g, AbstractAction cause) {
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
	
	static void buyCard(GameState g, Player p, Card card) {
		if(g.available.contains(card)){
			g.available.remove(card)
			p.discard.add(card)
		}
	}
}
