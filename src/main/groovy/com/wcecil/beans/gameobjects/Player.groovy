package com.wcecil.beans.gameobjects

import groovy.transform.Canonical
import groovy.transform.CompileStatic

import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document

import com.wcecil.beans.dto.User;
import com.wcecil.common.settings.Settings

@CompileStatic
@Document
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
	@Transient
	User user
	
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
