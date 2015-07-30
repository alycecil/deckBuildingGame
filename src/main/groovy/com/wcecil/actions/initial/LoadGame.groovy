package com.wcecil.actions.initial

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.wcecil.actions.AbstractAction
import com.wcecil.actions.core.DrawHand;
import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.ActualCard
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.CardTemplate;
import com.wcecil.beans.gameobjects.Player
import com.wcecil.core.GameController;
import com.wcecil.settings.Settings

class LoadGame extends AbstractAction{
	ObjectMapper mapper = new ObjectMapper()

	def doAction(GameState g) {
		List<Card> cards = loadCards(g)
		setupMainDeck(g,cards)
		setupPlayers(g, cards)
		Collections.shuffle(g.players)
		drawHands(g)
	}

	boolean isValid(GameState g) {
		g.players==null||g.players.isEmpty()
	}

	String audit() {
		"The game was loaded at {new Date()}"
	}

	def setupMainDeck(GameState g, List<Card> cards){
		cards.each { Card c->
			if(c.deckCount){
				(1..c.deckCount).each {
					def real = new ActualCard(c)
					g.mainDeck << real
				}
			}

			if(c.staticCount){
				Set<Card> staticSet = [] as Set

				(1..c.staticCount).each {
					def real = new ActualCard(c)
					staticSet << real
				}

				g.staticCards << staticSet
			}
		}

		if(Settings.debug) println "setup game state with a main deck of ${g.mainDeck.size()} and a static set of ${g.staticCards.size()}"
		Collections.shuffle(g.mainDeck)
	}

	def drawHands(GameState g){
		g.players.each {
			def drawHand = new DrawHand(targetPlayer:it, cause:this)
			GameController.doAction(g, drawHand)
		}
	}


	def setupPlayers(GameState g, List<Card> cards){
		(1..Settings.numPlayers).each {
			if(Settings.debug) println "Setting up player $it"
			g.players << newPlayerSetup(g,cards)
		}
	}

	Player newPlayerSetup(GameState g, List<Card> cards){
		Player result = new Player()

		cards.each { Card c->
			if(c.startingCount){
				(1..c.startingCount).each{
					def real = new ActualCard(c)
					result.discard << real
				}
			}
		}

		if(Settings.debug){
			println result
		}

		result
	}

	def loadCards(GameState g){
		def laodCards = new LoadCards(cause:this)
		return GameController.doAction(g, laodCards)
	}
}
