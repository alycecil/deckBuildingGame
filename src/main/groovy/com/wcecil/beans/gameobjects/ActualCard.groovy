package com.wcecil.beans.gameobjects

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wcecil.settings.Settings

class ActualCard extends CardTemplate {
	Long id

	@JsonIgnore
	@Delegate Card template
	
	public ActualCard(Card c) {
		template = c
		id = Settings.nextIdCard.getAndIncrement()
		if(Settings.debug) println "Created Card $id"
	}
	
	@Override
	public String toString() {
		return "${template?.getName()} #($id)";
	}

	
}
