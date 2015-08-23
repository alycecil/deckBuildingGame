package com.wcecil.game.actions.initial

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.CardTemplate
import com.wcecil.common.settings.Settings
import com.wcecil.common.util.StreamUtils
import com.wcecil.game.actions.Action
import com.wcecil.game.common.ScriptLoader
import com.wcecil.game.core.GameController

class LoadCards extends Action{
	static final String BASEDIR = 'static/cards/'
	ObjectMapper mapper = new ObjectMapper()

	def loadedCardsCount
	
	def doAction(GameState g, GameController gc) {
		return loadCards(g)
	}

	boolean isValid(GameState g) {
		g.players==null||g.players.isEmpty()
	}

	String getAudit() {
		"$loadedCardsCount Card(s) Loaded"
	}

	
	
	def loadCards(GameState g){
		InputStream result = this.getClass().getClassLoader().getResourceAsStream("${BASEDIR}set.json")
		List<String> setDefn = parseSetJSON(StreamUtils.convertStreamToString(result))
		try{result.close()}catch(ignored){}

		List<Card> cards = []
		setDefn.each{ /*println "loading $it";*/parseCards(it, cards) }

		debugDisplayLoadResults(cards)

		loadedCardsCount = cards.size()
		cards
	}

	void debugDisplayLoadResults(List cards) {
		if(!Settings.debug) return;
		println 'Loaded the following Cards'
		cards.each { println it }
	}

	def parseCards(String f, List cards) {
		InputStream result = this.getClass().getClassLoader().getResourceAsStream("${BASEDIR}${f}")
		def json = StreamUtils.convertStreamToString(result)
		try{result.close()}catch(ignored){}
		
		if(json){
			parseCardJSON(json, cards, f)
		}
	}

	List<String> parseSetJSON(String json) {
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, String.class)
		try {
			return mapper.readValue(json, type)
		} catch (e) {
			System.err.println("Error parsing $json, $e; stack trace to follow")
			e.printStackTrace()
		}
	}
	def parseCardJSON(String json, List cards, String f) {
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
