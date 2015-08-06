package com.wcecil.actions.core

import java.util.concurrent.ThreadLocalRandom

import com.wcecil.actions.Action
import com.wcecil.annotations.UserAction
import com.wcecil.beans.GameState

class DiscardCard extends Action {

	String audit = 'No Card to Discard'
	
	def doAction(GameState g) {
		if(targetPlayer.hand){
			int index = ThreadLocalRandom.current().nextInt(targetPlayer.hand.size());
			def card = targetPlayer.hand.remove(index)
			targetPlayer.discard << card
			
			audit = "Player ${targetPlayer.id} discarded the card '${card.name}'"
		}
	}

	boolean isValid(GameState g) {
		true
	}
}
