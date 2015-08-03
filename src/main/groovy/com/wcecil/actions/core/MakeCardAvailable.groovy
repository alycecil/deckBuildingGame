package com.wcecil.actions.core

import com.wcecil.actions.Action;
import com.wcecil.annotations.UserAction;
import com.wcecil.beans.GameState;

class MakeCardAvailable extends Action {
String audit
	def doAction(GameState g) {
		if(g.mainDeck){
			def top = g.mainDeck.remove(0)
			g.available << top
			
			audit="Made ${top.name} available for purchase"
		}else{
			audit='Main deck Exausted'
			//TODO trigger end of game
		}
	}

	public boolean isValid(GameState g) {
		g.mainDeck!=null
	}

}
