package com.wcecil.actions.core

import groovy.transform.CompileStatic

import com.wcecil.actions.AbstractAction
import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.CardMovementHelper;
import com.wcecil.core.GameController

class BuyCard extends AbstractAction {

	Card card
	String audit = 'Unable to buy card'

	def doAction(GameState g) {
		sourcePlayer.money -= card.cost
		
		CardMovementHelper.buyCard(g, sourcePlayer, card)

		audit = "Player ${sourcePlayer.id} bought the card '${card.name}' from the available cards"
	}



	boolean isValid(GameState g) {
		sourcePlayer.money>=card.cost
	}
}
