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
import com.wcecil.settings.Settings;

@UserAction
class EndGame extends Action {

	String audit = super.getAudit()

	def doAction(GameState g) {
		
		g.triggers = []
		
		def winningPlayer = "TIE"
		def winningScore = Integer.MIN_VALUE;
		
		g.players.each { Player p ->

			p.money = 0

			CardMovementHelper.moveFullZones(p.inplay, p.discard, g, this)
			CardMovementHelper.moveFullZones(p.played, p.discard, g, this)
			CardMovementHelper.moveFullZones(p.hand, p.discard, g, this)
			CardMovementHelper.moveFullZones(p.deck, p.discard, g, this)
			
			def score = 0
			p.discard.each {
				Card c ->
				score+=c.getValue()
			}
			
			p.score = score;
			
			if(p.score > winningScore){
				winningPlayer = "Player ${p.id}"
				winningScore = score
			}else if(p.score == winningScore){
				winningPlayer = "TIE"
			}
		}

		g.currentPlayer = null
		g.available = []
		g.allCards = []
		g.staticCards = []
		

		audit = "Player ${g.id} ended on ${new Date()}, the winner was ${winningPlayer} with a score of ${winningScore}"
		
		g.announcement = audit
		g.announcementType = AnnouncementType.success
	}

	boolean isValid(GameState g) {
		true
	}
}
