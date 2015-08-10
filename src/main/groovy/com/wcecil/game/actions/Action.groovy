package com.wcecil.game.actions

import groovy.transform.CompileStatic;

import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player

@CompileStatic
abstract class Action {
	Card sourceCard
	Card targetCard
	Player targetPlayer
	Player sourcePlayer
	Action cause
	
	abstract def doAction(GameState g)
	
	abstract boolean isValid(GameState g)
	
	String getAudit(){
		"An unknown action of ${this.getClass()} was performed"
	}
	
	def cleanAnnouncment(GameState g) {
		g.announcement = null;
		g.announcementType = null
	}
}
