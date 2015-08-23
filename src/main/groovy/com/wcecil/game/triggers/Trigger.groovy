package com.wcecil.game.triggers

import groovy.transform.CompileStatic;

import com.wcecil.beans.dto.GameState;
import com.wcecil.common.enums.Persistence
import com.wcecil.game.actions.Action

@CompileStatic
class Trigger {
	Persistence frequency
	Class<? extends Action> actionType
	String actionScript
	
	def doTrigger(GameState g, Action triggeringAction){
		def sharedData = new Binding()
		
		sharedData.setProperty('trigger', this)
		sharedData.setProperty('triggeringAction', triggeringAction)
		sharedData.setProperty('game', g)

		def shell = new GroovyShell(sharedData)

		shell.evaluate(getActionScript())
	}
	
	boolean isTriggered(Action a){
		actionType.isInstance(a)
	}
}
