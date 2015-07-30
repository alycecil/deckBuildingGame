package com.wcecil.core

import groovy.transform.CompileStatic;

import com.wcecil.actions.AbstractAction
import com.wcecil.beans.GameState

@CompileStatic
class GameController {

	static def doAction(GameState g, AbstractAction a){
		def result = null
		if(a.isValid(g)){
			result = a.doAction(g);

			def tic = g.ticCount.getAndIncrement()
			saveAudit(g, a)
		}else{
			throw new IllegalStateException("Game in illegal state for attempting $a on $g");
		}

		result
	}

	static void saveAudit(GameState g, AbstractAction a) {
		def tic = g.ticCount.get()
		if(a.audit){
			g.audit.add("$tic:${a.audit}".toString())
		}
	}
}
