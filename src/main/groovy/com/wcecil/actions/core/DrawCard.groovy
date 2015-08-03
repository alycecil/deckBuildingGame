package com.wcecil.actions.core

import groovy.transform.CompileStatic;

import com.wcecil.actions.Action
import com.wcecil.annotations.UserAction;
import com.wcecil.beans.GameState
import com.wcecil.common.CardMovementHelper;
import com.wcecil.core.GameController

@CompileStatic
class DrawCard extends Action {
	String audit = 'Unknown Error'

	def doAction(GameState g) {
		def card = CardMovementHelper.drawCard(targetPlayer,g,this);

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
