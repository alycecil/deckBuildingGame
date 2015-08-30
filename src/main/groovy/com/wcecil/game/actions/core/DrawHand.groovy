package com.wcecil.game.actions.core

import groovy.transform.CompileStatic

import com.wcecil.beans.dto.GameState
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.settings.Settings
import com.wcecil.game.actions.Action
import com.wcecil.game.core.GameController
import com.wcecil.game.util.CardMovementHelper
import com.wcecil.websocket.messanger.MessangerService;

@CompileStatic
class DrawHand extends Action {
	String audit = 'Unknown Error'

	def doAction(GameState g, GameController gc) {
		if(targetPlayer.hand){
			def discardCards = new DiscardHand(targetPlayer:targetPlayer, cause:this);
			gc.doAction(g, discardCards)
		}
		
		(1..Settings.defaultHandSize).each {
			CardMovementHelper.drawCard(targetPlayer,g,this,gc)
		}

		audit = "Player ${targetPlayer.id} drew a new hand"
	}

	boolean isValid(GameState g) {
		true
	}
	
	@Override
	public void sendNotification(GameState g, MessangerService messangerService) {
		messangerService.updateGame(g.id, targetPlayer.userId, this.class.name);
	}
}
