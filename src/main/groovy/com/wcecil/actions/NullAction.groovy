package com.wcecil.actions

import groovy.transform.CompileStatic;

import com.wcecil.beans.GameState;

@CompileStatic
class NullAction extends Action {

	def doAction(GameState g) {
		
	}

	boolean isValid(GameState g) {
		true
	}
}
