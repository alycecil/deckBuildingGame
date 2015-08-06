package com.wcecil.actions.core

import groovy.transform.CompileStatic

import com.wcecil.actions.Action
import com.wcecil.annotations.UserAction;
import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.CardMovementHelper;
import com.wcecil.core.GameController

@UserAction
class PlayCard extends Action {

	String audit = 'Unable to play card'

	def doAction(GameState g) {
		if(sourceCard.money){
			addMoney(sourcePlayer, sourceCard.money)
		}

		if(sourceCard.draw){
			drawCards(g, sourcePlayer, sourceCard.draw)
		}

		if(sourceCard.specialActionScript){
			sourceCard.specialAction(g, sourcePlayer, targetPlayer)
		}

		CardMovementHelper.playCard(sourcePlayer, sourceCard)

		audit = "Player ${sourcePlayer.id} played the card '${sourceCard.name}'"
	}



	boolean isValid(GameState g) {
		sourceCard!=null
	}

	def addMoney(Player player, Integer money){
		player.money+=money;
	}

	def drawCards(GameState g, Player player, Integer cards){
		(1..cards).each{
			def drawCard = new DrawCard(targetPlayer:sourcePlayer, cause:this)
			GameController.doAction(g, drawCard)
		}
	}
}
