package com.wcecil.game.rules

import groovy.transform.CompileStatic;

import com.wcecil.beans.GameState
import com.wcecil.game.actions.core.EndGame
import com.wcecil.game.core.GameController

@CompileStatic
class EndGameRule extends Rule{

	@Override
	public Object doRule(GameState g, GameController gc) {
		def triggered = false
		if(g.getCurrentPlayer()!=null){
			if(!g.mainDeck){
				triggered = true;
			}
		}

		if(triggered){
			gc.doAction(g, new EndGame())
		}
	}
}
