package com.wcecil.game.interactions

import groovy.transform.CompileStatic;

import com.wcecil.beans.dto.GameState;
import com.wcecil.beans.gameobjects.Player
import com.wcecil.game.actions.Action;
import com.wcecil.game.triggers.Trigger;

@CompileStatic
interface Interaction {
	boolean done
	Player targetPlayer
	Action sourceAction
	Trigger sourceTrigger
	
	def start(GameState g)
	
}
