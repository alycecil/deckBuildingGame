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

	Card card
	String audit = 'Unable to play card'

	def doAction(GameState g) {
		if(card.money){
			addMoney(sourcePlayer, card.money)
		}

		if(card.draw){
			drawCards(sourcePlayer, card.money)
		}

		if(card.specialActionScript){
			card.specialAction(g, sourcePlayer, targetPlayer)
		}

		CardMovementHelper.playCard(sourcePlayer, card)

		audit = "Player ${sourcePlayer.id} played the card '${card.name}'"
	}



	boolean isValid(GameState g) {
		card!=null
	}

	def addMoney(Player player, Integer money){
		player.money+=money;
	}

	def drawCards(Player player, Integer cards){
		def drawCard = new DrawCard(targetPlayer:targetPlayer, cause:this)
		GameController.doAction(g, drawCard)
	}
}
