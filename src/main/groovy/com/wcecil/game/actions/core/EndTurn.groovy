package com.wcecil.game.actions.core

import com.wcecil.beans.dto.GameState
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.annotations.UserAction
import com.wcecil.common.settings.Settings
import com.wcecil.game.actions.Action
import com.wcecil.game.core.GameController
import com.wcecil.game.util.CardMovementHelper
import com.wcecil.websocket.messanger.MessangerService

@UserAction
class EndTurn extends Action {

	String audit = super.getAudit()

	def doAction(GameState g, GameController gc) {
		Player currentPlayer  = g.currentPlayer

		Player nextPlayer = null
		int index = g.players.indexOf(currentPlayer)
		index = (index+1) % g.players.size()
		nextPlayer = g.players.get(index)

		g.players.each { Player p ->

			p.money = 0

			CardMovementHelper.moveFullZones(p.played, p.discard, g, this)
		}

		CardMovementHelper.moveFullZones(currentPlayer.hand, currentPlayer.discard, g, this)
		gc.doAction(g, new DrawHand(targetPlayer:currentPlayer))
		
		while(g.available.size()<Settings.defaultAvailableSize){
			gc.doAction(g, new MakeCardAvailable(cause:this))
		}
		
		g.currentPlayer = nextPlayer

		audit = "Player ${currentPlayer.id} ended their turn, starting the turn of Player ${nextPlayer.id}"
		
		cleanAnnouncment(g);
	}

	boolean isValid(GameState g) {
		true
	}

	@Override
	public void sendNotification(GameState g, MessangerService messangerService) {
		messangerService.endTurn(g.id);
	}
}
