package com.wcecil.game.actions.initial

import com.fasterxml.jackson.databind.ObjectMapper
import com.wcecil.beans.dto.GameState;
import com.wcecil.beans.gameobjects.ActualCard
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.settings.Settings
import com.wcecil.game.actions.Action
import com.wcecil.game.actions.core.DrawHand
import com.wcecil.game.actions.core.MakeCardAvailable
import com.wcecil.game.core.GameController
import com.wcecil.game.rules.EndGameRule

class LoadGame extends Action{
	ObjectMapper mapper = new ObjectMapper()

	def doAction(GameState g, GameController gc) {
		List<Card> cards = loadCards(g, gc)
		setupMainDeck(g,cards,gc)
		setupPlayers(g, cards)

		println "Added ${g.allCards.size()}"
		drawHands(g, gc)

		addRules(g)
	}

	def addRules(GameState g) {
		g.rules << new EndGameRule()
	}

	boolean isValid(GameState g) {
		g.players==null||g.players.isEmpty()
	}

	String getAudit() {
		"The game was loaded at ${new Date()}"
	}

	def setupMainDeck(GameState g, List<Card> cards, GameController gc){
		cards.each { Card c->
			if(c.deckCount){
				(1..c.deckCount).each {
					def real = new ActualCard(c)
					g.mainDeck << real
					if(Settings.debug) println "Added ${real.id}"
					g.allCards << real
				}
			}

			if(c.staticCount){
				List<Card> staticSet = []

				(1..c.staticCount).each {
					def real = new ActualCard(c)
					staticSet << real
					if(Settings.debug) println "Added ${real.id}"
					g.allCards << real
				}

				g.staticCards << staticSet
			}
		}

		if(Settings.debug) println "setup game state with a main deck of ${g.mainDeck.size()} and a static set of ${g.staticCards.size()}"
		Collections.shuffle(g.mainDeck)

		(1..Settings.defaultAvailableSize).each {
			gc.doAction(g, new MakeCardAvailable(cause:this))
		}
	}

	def drawHands(GameState g, GameController gc){
		g.players.each {
			def drawHand = new DrawHand(targetPlayer:it, cause:this)
			gc.doAction(g, drawHand)
		}
	}


	def setupPlayers(GameState g, List<Card> cards){
		(1..Settings.numPlayers).each {
			if(Settings.debug) println "Setting up player $it"
			g.players << newPlayerSetup(g,cards)
		}

		Collections.shuffle(g.players)

		g.currentPlayer = g.players.get(0)
	}

	Player newPlayerSetup(GameState g, List<Card> cards){
		Player result = new Player()

		cards.each { Card c->
			if(c.startingCount){
				(1..c.startingCount).each{
					def real = new ActualCard(c)
					result.discard << real
					if(Settings.debug) println "Added ${real.id}"
					g.allCards << real
				}
			}
		}

		if(Settings.debug){
			println result
		}

		result
	}

	def loadCards(GameState g, GameController gc){
		def laodCards = new LoadCards(cause:this)
		return gc.doAction(g, laodCards)
	}
}
