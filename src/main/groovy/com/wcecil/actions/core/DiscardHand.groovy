package com.wcecil.actions.core

import com.wcecil.actions.AbstractAction;
import com.wcecil.beans.GameState;
import com.wcecil.core.GameController;

class DiscardHand extends AbstractAction {

	def doAction(GameState g) {
		targetPlayer.discard.addAll(targetPlayer.hand)
		targetPlayer.hand.clear()
			
	}

	boolean isValid(GameState g) {
		true
	}
}
