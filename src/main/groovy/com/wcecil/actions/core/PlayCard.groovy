package com.wcecil.actions.core

import groovy.transform.CompileStatic

import com.wcecil.actions.AbstractAction
import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.core.GameController

class PlayCard extends AbstractAction {

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
		
		audit = "Player ${targetPlayer.id} played the card '${card.name}'"
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
