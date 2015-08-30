package com.wcecil.game.actions.core

import com.wcecil.beans.dto.GameState;
import com.wcecil.game.actions.Action
import com.wcecil.game.core.GameController
import com.wcecil.websocket.messanger.MessangerService;

class MakeCardAvailable extends Action {
String audit
	def doAction(GameState g, GameController gc) {
		if(g.mainDeck){
			def top = g.mainDeck.remove(0)
			g.available << top
			
			audit="Made ${top.name} available for purchase"
		}else{
			audit='Main deck Exausted'
			//TODO trigger end of game
		}
	}

	public boolean isValid(GameState g) {
		g.mainDeck!=null
	}
	
	@Override
	public void sendNotification(GameState g, MessangerService messangerService) {
		messangerService.updateGame(g.id, null, Action.getActionMessage(this));
	}

}
