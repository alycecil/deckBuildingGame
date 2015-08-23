package com.wcecil.game.actions.core

import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.annotations.UserAction
import com.wcecil.common.enums.AnnouncementType
import com.wcecil.game.actions.Action
import com.wcecil.game.common.CardMovementHelper
import com.wcecil.game.core.GameController

@UserAction
class BuyCard extends Action {

	String audit = 'Unable to buy card'

	def doAction(GameState g, GameController gc) {
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
