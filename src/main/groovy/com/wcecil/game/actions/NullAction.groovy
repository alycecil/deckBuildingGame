package com.wcecil.game.actions

import groovy.transform.CompileStatic;

import com.wcecil.beans.GameState;
import com.wcecil.game.core.GameController

@CompileStatic
class NullAction extends Action {

	def doAction(GameState g, GameController gc) {
		
	}

	boolean isValid(GameState g) {
		true
	}
}
