package com.wcecil.beans.gameobjects

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wcecil.settings.Settings

class ActualCard extends CardTemplate {
	Long id = Settings.nextIdCard.getAndIncrement()

	@JsonIgnore
	@Delegate Card template
	
	public ActualCard(Card c) {
		template = c
	}
	
	@Override
	public String toString() {
		return "${template?.getName()} #($id)";
	}

	
}
