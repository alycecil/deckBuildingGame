package com.wcecil.beans.gameobjects

import groovy.transform.Canonical;
import groovy.transform.CompileStatic;

import com.wcecil.settings.Settings

@CompileStatic
@Canonical
class Player {
	Integer id
	List<Card> deck
	List<Card> discard
	List<Card> inplay
	List<Card> played
	List<Card> hand
	
	Integer money = 0l
	public Player() {
		super();

		id = Settings.nextIdPlayer.getAndIncrement()

		deck=[]
		discard=[]
		inplay=[]
		played=[]
		hand=[]
	}
}
