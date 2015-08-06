package com.wcecil.rules

import com.wcecil.actions.Action
import com.wcecil.actions.core.EndGame;
import com.wcecil.beans.GameState
import com.wcecil.core.GameController;
import com.wcecil.enums.Persistence;

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
