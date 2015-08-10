package com.wcecil.game.actions.initial

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.CardTemplate
import com.wcecil.common.settings.Settings
import com.wcecil.game.actions.Action
import com.wcecil.game.common.ScriptLoader

class LoadCards extends Action{
	ObjectMapper mapper = new ObjectMapper()

	def loadedCardsCount
	
	def doAction(GameState g) {
		return loadCards(g)
	}

	boolean isValid(GameState g) {
		g.players==null||g.players.isEmpty()
	}

	String getAudit() {
		"$loadedCardsCount Card(s) Loaded"
	}

	def loadCards(GameState g){
		URL cardsDir = this.getClass().getClassLoader().getResource('static/cards')
		if(!cardsDir){
			throw new IllegalStateException('Unable to load cards')
		}

		File f = new File(cardsDir.toURI())

		if(!f.exists()){
			throw new IllegalStateException('Unable to load cards')
		}

		List<Card> cards = []
		f.listFiles().each{ readCardFile(it, cards) }

		debugDisplayLoadResults(cards)

		loadedCardsCount = cards.size()
		cards
	}

	void debugDisplayLoadResults(List cards) {
		if(!Settings.debug) return;
		println 'Loaded the following Cards'
		cards.each { println it }
	}

	def readCardFile(File f, List<Card> cards){

		if(f.isDirectory()){
			readCardFile(f,cards)
		}else if(f.isFile()){
			parseCards(f, cards)
		}
	}

	def parseCards(File f, List cards) {
		def json = f.text
		if(json){
			parseCardJSON(json, cards, f)
		}
	}

	def parseCardJSON(String json, List cards, File f) {
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, CardTemplate.class)
		try {
			List<Card> myCards = mapper.readValue(json, type)
			def scrLoader = new ScriptLoader()
			if(myCards){
				myCards.each{
					Card c ->
					c.specialActionScript=scrLoader.loadScript(c.specialActionScript);
				}
				cards.addAll(myCards)
			}
		} catch (e) {
			System.err.println("Error reading $f, $e; stack trace to follow")
			e.printStackTrace()
		}
	}
}
