package com.wcecil.game.actions.core

import groovy.transform.CompileStatic

import com.wcecil.beans.dto.GameState
import com.wcecil.beans.gameobjects.Player
import com.wcecil.game.actions.Action
import com.wcecil.game.core.GameController
import com.wcecil.game.util.CardMovementHelper
import com.wcecil.websocket.messanger.MessangerService

@CompileStatic
class ShuffleDiscardIntoDeck extends Action {
	String audit = 'Unknown Error'
	
	def doAction(GameState g, GameController gc) {
		if(CardMovementHelper.moveDiscardToDeckAndShuffle(targetPlayer,g,this)){
			audit = "Player ${targetPlayer.id} Shuffled Discard Pile into Deck"
		}else{
			audit = null
		}
		
		true
	}

	

	boolean isValid(GameState g) {
		targetPlayer.discard!=null && targetPlayer.deck!=null
	}
	
	@Override
	public void sendNotification(GameState g, MessangerService messangerService) {
		messangerService.updateGame(g?.id, targetPlayer?.userId, Action.getActionMessage(this));
	}
}
