package com.wcecil.beans.gameobjects

import groovy.transform.Canonical
import groovy.transform.CompileStatic

import com.wcecil.common.settings.Settings

@CompileStatic
@Canonical
class Player {
	String userId
	
	Long id
	List<Card> deck
	List<Card> discard
	List<Card> inplay
	List<Card> played
	List<Card> hand
	
	Integer money = 0
	
	Integer score = 0
	public Player() {
		super();

		id = Settings.nextIdPlayer.getAndIncrement()

		deck=[]
		discard=[]
		inplay=[]
		played=[]
		hand=[]
	}
	@Override
	public String toString() {
		return "Player [id=" + id + "\n\t, deck=" + deck + "\n\t, discard=" + discard + "\n\t, inplay=" + inplay + "\n\t, played=" + played + "\n\t, hand=" + hand + "\n\t, money=" + money + "]";
	}
	
	
}
