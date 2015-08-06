package com.wcecil.actions.core

import com.wcecil.actions.Action
import com.wcecil.annotations.UserAction
import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.CardMovementHelper
import com.wcecil.enums.AnnouncementType;

@UserAction
class BuyCard extends Action {

	String audit = 'Unable to buy card'

	def doAction(GameState g) {
		if(sourcePlayer.money < targetCard.cost){
			audit = "Current money is only ${sourcePlayer.money} which is less than the required ${targetCard.cost}";

			g.announcement= audit

			g.announcementType = AnnouncementType.danger
		}else{
			sourcePlayer.money -= targetCard.cost

			CardMovementHelper.buyCard(g, sourcePlayer, targetCard)

			audit = "Player ${sourcePlayer.id} bought the card '${targetCard.name}' from the available cards"
		}
	}

	boolean isValid(GameState g) {
		g.available.contains(targetCard)
	}
}
