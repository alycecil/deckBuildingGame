package com.wcecil.actions.initial

import groovy.transform.CompileStatic;

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

class LoadCards extends AbstractAction{
	ObjectMapper mapper = new ObjectMapper()

	def loadedCardsCount
	
	def doAction(GameState g) {
		return loadCards(g)
	}

	boolean isValid(GameState g) {
		g.players==null||g.players.isEmpty()
	}

	String audit() {
		"$loadedCardsCount Card(s) Loaded"
	}

	def loadCards(GameState g){
		URL cardsDir = this.getClass().getClassLoader().getResource('cards')
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
			def json = f.text
			if(json){
				JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, CardTemplate.class)
				try {
					List<Card> myCards = mapper.readValue(json, type)

					if(myCards){
						cards.addAll(myCards)
					}
				} catch (e) {
					System.err.println("Error reading $f, $e; stack trace to follow")
					e.printStackTrace()
				}
			}
		}
	}
}
