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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActualCard other = (ActualCard) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
