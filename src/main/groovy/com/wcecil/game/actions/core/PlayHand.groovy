package com.wcecil.game.actions.core

import com.wcecil.beans.dto.GameState;
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.annotations.UserAction
import com.wcecil.common.enums.AnnouncementType
import com.wcecil.game.actions.Action
import com.wcecil.game.core.GameController

@UserAction
class PlayHand extends Action {

	String audit = 'Unable to play hand'

	def doAction(GameState g, GameController gc) {
		
		cleanAnnouncment(g)
		
		Set<Long> keys = [] as Set
		while(!g.currentPlayer.hand.isEmpty()) {
			Card c = g.currentPlayer.hand.get(0)
			
			if(keys.contains(c.id)){
				g.announcement = "Unable to play anymore cards"
				g.announcementType = AnnouncementType.danger
				break;
			}else{
				keys.add(c.id)
			}

			gc.doAction(g, new PlayCard(sourceCard:c, sourcePlayer:g.currentPlayer))
		}
		
		audit = "Player ${sourcePlayer.id} played all the cards in their hand"
		
		if(!g.announcement){
			g.announcement = audit
			g.announcementType = AnnouncementType.success
		}
	}

	boolean isValid(GameState g) {
		sourcePlayer!=null
	}
}
