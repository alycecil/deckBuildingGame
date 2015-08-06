package com.wcecil.actions.core

import groovy.transform.CompileStatic

import com.wcecil.actions.Action
import com.wcecil.annotations.UserAction;
import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.CardMovementHelper;
import com.wcecil.core.GameController
import com.wcecil.enums.AnnouncementType;

@UserAction
class PlayHand extends Action {

	String audit = 'Unable to play hand'

	def doAction(GameState g) {
		
		cleanAnnouncment(g)
		
		Set<Long> keys = [] as Set
		while(!g.currentPlayer.hand.isEmpty()) {
			def c = g.currentPlayer.hand.get(0)
			
			if(keys.contains(c.id)){
				g.announcement = "Unable to play anymore cards"
				g.announcementType = AnnouncementType.danger
				break;
			}else{
				keys.add(c.id)
			}

			GameController.doAction(g, new PlayCard(sourceCard:c, sourcePlayer:g.currentPlayer))
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
