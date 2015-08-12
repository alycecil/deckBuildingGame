package com.wcecil.beans

import groovy.transform.CompileStatic

import java.util.concurrent.atomic.AtomicLong

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document

import com.fasterxml.jackson.annotation.JsonIgnore
import com.wcecil.beans.gameobjects.Card
import com.wcecil.beans.gameobjects.Player
import com.wcecil.data.objects.GameAudit;
import com.wcecil.game.rules.Rule
import com.wcecil.game.triggers.Trigger

@CompileStatic
@Document
class GameState {
	@Id
	String id

	AtomicLong ticCount = new AtomicLong(0l);

	List<Player> players = []

	@JsonIgnore
	private Long currentPlayerId;

	@Transient
	Player currentPlayer;

	List<Card> available = []
	List<Card> mainDeck = []
	List<List<Card>> staticCards = []

	@Transient
	List<GameAudit> audit = []

	@JsonIgnore
	Set<Trigger> triggers = [] as Set
	@JsonIgnore
	List<Card> allCards = []

	@JsonIgnore
	List<Rule> rules = []

	String announcement
	def announcementType

	@Transient
	public Player getCurrentPlayer() {
		if(!currentPlayer){
			for(Player p : players){
				if(p&&p.id&&p.id.equals(currentPlayerId)){
					currentPlayer = p;
					break;
				}
			}
		}
		return currentPlayer;
	}
	
	@Transient
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		this.currentPlayerId = currentPlayer.getId();
	}
}
