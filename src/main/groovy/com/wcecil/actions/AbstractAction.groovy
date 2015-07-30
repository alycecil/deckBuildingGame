package com.wcecil.actions

import groovy.transform.CompileStatic;

import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player

@CompileStatic
abstract class AbstractAction {
	Card sourceCard
	Player targetPlayer
	Player sourcePlayer
	AbstractAction cause
	
	abstract def doAction(GameState g)
	
	abstract boolean isValid(GameState g)
	
	String getAudit(){
		"An unknown action of ${this.getClass()} was performed"
	}
}
