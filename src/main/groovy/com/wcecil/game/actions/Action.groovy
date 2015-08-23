package com.wcecil.game.actions

import groovy.transform.CompileStatic;

import com.wcecil.beans.dto.GameState;
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.game.core.GameController
import com.wcecil.websocket.messanger.MessangerService;

@CompileStatic
abstract class Action {
	Card sourceCard
	Card targetCard
	Player targetPlayer
	Player sourcePlayer
	Action cause
	
	abstract def doAction(GameState g, GameController gc)
	
	abstract boolean isValid(GameState g)
	
	void sendNotification(GameState g, MessangerService messangerService){
		
	}
	
	String getAudit(){
		"An unknown action of ${this.getClass()} was performed"
	}
	
	def cleanAnnouncment(GameState g) {
		g.announcement = null;
		g.announcementType = null
	}
}
