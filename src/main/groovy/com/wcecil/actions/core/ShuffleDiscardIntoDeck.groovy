package com.wcecil.actions.core

import groovy.transform.CompileStatic

import com.wcecil.actions.AbstractAction
import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.CardMovementHelper

@CompileStatic
class ShuffleDiscardIntoDeck extends AbstractAction {
	String audit = 'Unknown Error'
	
	def doAction(GameState g) {
		if(CardMovementHelper.moveDiscardToDeckAndShuffle(targetPlayer,g,this)){
			audit = 'Shuffled Discard Pile into Deck'
		}else{
			audit = null
		}
		
		true
	}

	

	boolean isValid(GameState g) {
		targetPlayer.discard!=null && targetPlayer.deck!=null
	}
}
