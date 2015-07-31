package com.wcecil.actions.core

import com.wcecil.actions.Action;
import com.wcecil.beans.GameState;
import com.wcecil.core.GameController;

class DiscardHand extends Action {

	def doAction(GameState g) {
		targetPlayer.discard.addAll(targetPlayer.hand)
		targetPlayer.hand.clear()
			
	}

	boolean isValid(GameState g) {
		true
	}
}
