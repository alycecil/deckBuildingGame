package com.wcecil.game.actions.core

import com.wcecil.beans.dto.GameState;
import com.wcecil.game.actions.Action
import com.wcecil.game.core.GameController;

class DiscardHand extends Action {

	def doAction(GameState g, GameController gc) {
		targetPlayer.discard.addAll(targetPlayer.hand)
		targetPlayer.hand.clear()
			
	}

	boolean isValid(GameState g) {
		true
	}
}
