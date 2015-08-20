package com.wcecil.game.rules

import groovy.transform.CompileStatic;

import com.wcecil.beans.GameState;

@CompileStatic
abstract class Rule {
	abstract def doRule(GameState g);
}
