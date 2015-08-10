package com.wcecil.beans

import groovy.transform.CompileStatic

import java.util.concurrent.atomic.AtomicLong

import com.fasterxml.jackson.annotation.JsonIgnore
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.settings.Settings
import com.wcecil.game.rules.Rule
import com.wcecil.game.triggers.Trigger

//TODO port to jpa/mongo/or some similar nonsense
@CompileStatic
class GameState {
	Long id;

	AtomicLong ticCount = new AtomicLong(0l);

	List<Player> players = []
	Player currentPlayer

	List<Card> available = []
	List<Card> mainDeck = []
	List<List<Card>> staticCards = []

	List<String> audit = []

	@JsonIgnore
	Set<Trigger> triggers = [] as Set
	@JsonIgnore
	List<Card> allCards = []
	
	@JsonIgnore
	List<Rule> rules = []
	
	String announcement
	def announcementType

	public GameState() {
		this(true);
	}

	public GameState(boolean updateId) {
		if(updateId){
			id = Settings.nextIdGame.getAndIncrement();
			
			if(Settings.debug) println "Created Game $id"
		}
	}
}
