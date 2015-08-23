package com.wcecil.game.rules

import groovy.transform.CompileStatic;

import com.wcecil.beans.GameState;
import com.wcecil.game.core.GameController

@CompileStatic
abstract class Rule {
	abstract def doRule(GameState g, GameController gc);
}
