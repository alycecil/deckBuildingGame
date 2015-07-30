package com.wcecil.beans.gameobjects

import com.wcecil.settings.Settings

class ActualCard implements Card {
	Integer id = Settings.nextIdCard.getAndIncrement()

	@Delegate Card template
	public ActualCard(Card c) {
		super();
		template = c
	}
	
	@Override
	public String toString() {
		return "${template?.getName()} #($id)";
	}

	
}
