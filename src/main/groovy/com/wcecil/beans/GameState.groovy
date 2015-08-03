package com.wcecil.beans

import groovy.transform.CompileStatic

import java.util.concurrent.atomic.AtomicLong

import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.settings.Settings;
import com.wcecil.triggers.Trigger

//TODO port to jpa/mongo/or some similar nonsense
@CompileStatic
class GameState {
	Long id;

	AtomicLong ticCount = new AtomicLong(0l);

	List<Player> players = []
	Player currentPlayer

	List<Card> available = []
	List<Card> mainDeck = []
	List<Set<Card>> staticCards = []

	List<String> audit = []

	Set<Trigger> triggers = [] as Set

	public GameState() {
		this(true);
	}

	public GameState(boolean updateId) {
		if(updateId){
			id = Settings.nextIdGame.getAndIncrement();
		}
	}


}
