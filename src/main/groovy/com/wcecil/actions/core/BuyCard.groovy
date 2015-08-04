package com.wcecil.actions.core

import com.wcecil.actions.Action
import com.wcecil.annotations.UserAction
import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.CardMovementHelper

@UserAction
class BuyCard extends Action {
	
	String audit = 'Unable to buy card'

	def doAction(GameState g) {
		if(sourcePlayer.money < targetCard.cost){
			throw new IllegalStateException("Need at least ${sourcePlayer.money} is less than ${card.cost}");
		}
		
		sourcePlayer.money -= targetCard.cost
		
		CardMovementHelper.buyCard(g, sourcePlayer, targetCard)

		audit = "Player ${sourcePlayer.id} bought the card '${card.name}' from the available cards"
	}



	boolean isValid(GameState g) {
		sourcePlayer.money>=targetCard.cost && g.available.contains(targetCard)
	}
}
