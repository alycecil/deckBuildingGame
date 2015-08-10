package com.wcecil.game.rules

import com.wcecil.beans.GameState
import com.wcecil.game.actions.core.EndGame
import com.wcecil.game.core.GameController

class EndGameRule extends Rule{

	@Override
	public Object doRule(GameState g) {
		def triggered = false
		if(g.getCurrentPlayer()!=null){
			if(!g.mainDeck){
				triggered = true;
			}
		}

		if(triggered){
			GameController.doAction(g, new EndGame())
		}
	}
}
