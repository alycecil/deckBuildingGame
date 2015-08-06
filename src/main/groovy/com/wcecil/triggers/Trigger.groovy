package com.wcecil.triggers

import com.wcecil.actions.Action
import com.wcecil.beans.GameState
import com.wcecil.enums.Persistence;

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
