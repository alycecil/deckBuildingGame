package com.wcecil.actions.core

import groovy.transform.CompileStatic

import com.wcecil.actions.AbstractAction
import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.CardMovementHelper;
import com.wcecil.core.GameController

class EndTurn extends AbstractAction {

	String audit = super.getAudit()

	def doAction(GameState g) {
		Player currentPlayer  =g.currentPlayer
		
		Player nextPlayer = null
		int index = g.players.indexOf(currentPlayer)
		index++
		index = index % g.players.size()
		nextPlayer = g.players.get(index)
		
		g.players.each {
			Player p ->
			
			p.money = 0
			
			CardMovementHelper.moveFullZones(p.played, p.discard, g, this)
		}
		
		CardMovementHelper.moveFullZones(currentPlayer.hand, currentPlayer.discard, g, this)
		GameController.doAction(g, new DrawHand(targetPlayer:currentPlayer))
		
		audit = "Player ${currentPlayer.id} ended their turn, starting the turn of Player ${nextPlayer.id}"
	}

	boolean isValid(GameState g) {
		true
	}
}
