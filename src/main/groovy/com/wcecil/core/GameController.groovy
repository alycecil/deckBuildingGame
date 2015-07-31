package com.wcecil.core

import groovy.transform.CompileStatic

import com.wcecil.actions.Action
import com.wcecil.beans.GameState
import com.wcecil.settings.Settings
import com.wcecil.triggers.Trigger

@CompileStatic
class GameController {

	static def doAction(GameState g, Action a){
		def result = null
		if(a.isValid(g)){
			
			doTriggers( g,  a)
			
			result = a.doAction(g);

			def tic = g.ticCount.getAndIncrement()
			saveAudit(g, a)
		}else{
			throw new IllegalStateException("Game in illegal state for attempting $a on $g");
		}

		result
	}
	
	static void doTriggers(GameState g, Action a){
		g.triggers.each {
			Trigger t ->
			if(t.isTriggered(a)){
				t.doTrigger(g, a)
			}
		}	
	}

	static void saveAudit(GameState g, Action a) {
		def tic = g.ticCount.get()
		if(a.audit){
			def audit = "$tic:${a.audit}".toString()
			if(Settings.debug) println audit
			g.audit.add(audit)
		}
	}
}
