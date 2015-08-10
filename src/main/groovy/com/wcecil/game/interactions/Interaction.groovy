package com.wcecil.game.interactions

import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Player
import com.wcecil.game.actions.Action;
import com.wcecil.game.triggers.Trigger;

interface Interaction {
	boolean done
	Player targetPlayer
	Action sourceAction
	Trigger sourceTrigger
	
	def start(GameState g)
	
}
