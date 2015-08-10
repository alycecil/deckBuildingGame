package com.wcecil.game.actions.core

import com.wcecil.beans.GameState
import com.wcecil.game.actions.Action

class DiscardHand extends Action {

	def doAction(GameState g) {
		targetPlayer.discard.addAll(targetPlayer.hand)
		targetPlayer.hand.clear()
			
	}

	boolean isValid(GameState g) {
		true
	}
}
