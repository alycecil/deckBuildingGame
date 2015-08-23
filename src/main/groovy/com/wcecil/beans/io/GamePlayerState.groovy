package com.wcecil.beans.io

import com.wcecil.beans.dto.GameState;

import groovy.transform.CompileStatic;

@CompileStatic
class GamePlayerState {
	List<GameState> games;
	String playerName;
	String id;
}
