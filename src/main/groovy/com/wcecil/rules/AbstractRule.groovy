package com.wcecil.rules

import com.wcecil.beans.GameState;

abstract class AbstractRule {
	abstract def doRule(GameState g);
}
