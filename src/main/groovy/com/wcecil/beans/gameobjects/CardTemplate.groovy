package com.wcecil.beans.gameobjects

import groovy.transform.Canonical;
import groovy.transform.CompileStatic;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


	def specialAction(GameState game, Player source, Player target){
		def sharedData = new Binding()

		sharedData.setProperty('card', this)
		sharedData.setProperty('source', source)
		sharedData.setProperty('target', target)
		sharedData.setProperty('game', game)

		def shell = new GroovyShell(sharedData)

		shell.evaluate(getSpecialActionScript())
	}
}
