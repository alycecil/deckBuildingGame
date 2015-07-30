package com.wcecil.beans.rules

import com.wcecil.beans.gameobjects.Player

class Step {
	Player owner
	String description
	List<Action> stack
	List<Action> resolved
}
