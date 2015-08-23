package com.wcecil.game.actions.core

import groovy.transform.CompileStatic;

import com.wcecil.beans.dto.GameState;
import com.wcecil.game.actions.Action;
import com.wcecil.game.core.GameController

@CompileStatic
class NullAction extends Action {

	def doAction(GameState g, GameController gc) {
		
	}

	boolean isValid(GameState g) {
		true
	}
}
