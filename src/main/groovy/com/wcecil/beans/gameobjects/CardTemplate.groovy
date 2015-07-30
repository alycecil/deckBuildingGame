package com.wcecil.beans.gameobjects

import groovy.transform.Canonical;
import groovy.transform.CompileStatic;

import com.wcecil.beans.GameState

@CompileStatic
@Canonical
class CardTemplate implements Card {
	Integer cost
	Integer money
	Integer draw
	Integer value

	String name
	String description

	String specialActionScript
	
	Integer startingCount
	Integer deckCount
	Integer staticCount


	def specialAction(GameState game){
		def sharedData = new Binding()

		sharedData.setProperty('card', this)
		sharedData.setProperty('players', game.players)
		sharedData.setProperty('currPlayer', game.currentPlayer)
		sharedData.setProperty('game', game)

		def shell = new GroovyShell(sharedData)

		shell.evaluate(getSpecialActionScript())
	}
}
