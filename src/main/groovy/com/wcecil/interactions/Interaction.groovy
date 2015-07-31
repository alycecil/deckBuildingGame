package com.wcecil.interactions

import com.wcecil.actions.Action
import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Player
import com.wcecil.triggers.Trigger

interface Interaction {
	boolean done
	Player targetPlayer
	Action sourceAction
	Trigger sourceTrigger
	
	def start(GameState g)
	
}
