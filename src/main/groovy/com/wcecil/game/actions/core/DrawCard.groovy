package com.wcecil.game.actions.core

import groovy.transform.CompileStatic

import com.wcecil.beans.GameState
import com.wcecil.game.actions.Action
import com.wcecil.game.common.CardMovementHelper
import com.wcecil.game.core.GameController

@CompileStatic
class DrawCard extends Action {
	String audit = 'Unknown Error'

	def doAction(GameState g, GameController gc) {
		def card = CardMovementHelper.drawCard(targetPlayer,g,this,gc);

		if(card){
			audit = "Player ${targetPlayer.id} drew a card"
		}else{
			audit = "Player ${targetPlayer.id} failed to draw a card"
		}
	}

	boolean isValid(GameState g) {
		true
	}
}
