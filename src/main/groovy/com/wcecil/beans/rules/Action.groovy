package com.wcecil.beans.rules

import com.wcecil.beans.gameobjects.Card;
import com.wcecil.beans.gameobjects.Player;

class Action {
	long timeStamp
	long delay
	
	boolean instant
	boolean needsUser
	
	Card cause
	Player target
	Player source
	Step targetStep
}
