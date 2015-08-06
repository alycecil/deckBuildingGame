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
			audit = "Current money is only ${sourcePlayer.money} <i class='fa fa-money'></i> which is less than the required ${targetCard.cost} <i class='fa fa-money'></i>";

			g.announcement= audit

			g.announcementType = AnnouncementType.danger
		}else{
			if(CardMovementHelper.buyCard(g, sourcePlayer, targetCard)){
				sourcePlayer.money -= targetCard.cost

				audit = "Player ${sourcePlayer.id} bought the card '${targetCard.name}' from the available cards"

				cleanAnnouncment(g);
			}else{
				audit = "Unable to find ${targetCard.name} in a buyable zone";

				g.announcement= audit

				g.announcementType = AnnouncementType.danger
			}
		}
	}

	boolean isValid(GameState g) {
		boolean inStatic = false
		g.staticCards.each {
			inStatic = inStatic||it.contains(targetCard)
		}

		g.available.contains(targetCard) || inStatic
	}
}
