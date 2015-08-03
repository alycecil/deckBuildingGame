package com.wcecil.actions.core

import groovy.transform.CompileStatic

import com.wcecil.actions.Action
import com.wcecil.annotations.UserAction;
import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.CardMovementHelper;
import com.wcecil.core.GameController
import com.wcecil.settings.Settings;

@CompileStatic
class DrawHand extends Action {
	String audit = 'Unknown Error'

	def doAction(GameState g) {
		if(targetPlayer.hand){
			def discardCards = new DiscardHand(targetPlayer:targetPlayer, cause:this);
			GameController.doAction(g, discardCards)
		}
		
		(1..Settings.defaultHandSize).each {
			CardMovementHelper.drawCard(targetPlayer,g,this)
		}

		audit = "Player ${targetPlayer.id} drew a new hand"
	}

	boolean isValid(GameState g) {
		true
	}
}
