package com.wcecil.websocket.messages;

import com.wcecil.beans.gameobjects.Card;
import com.wcecil.beans.gameobjects.Player;

public class ActionMessage {
	Card sourceCard
	Card targetCard
	Player targetPlayer
	Player sourcePlayer
	String type;
}
