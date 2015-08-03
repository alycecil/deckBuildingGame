package com.wcecil

import static org.junit.Assert.*

import org.junit.Test

import com.wcecil.actions.core.EndTurn;
import com.wcecil.beans.GameState;
import com.wcecil.core.GameController;

class GameControllerTest {

	@Test
	void textbuildAction(){
		GameState g = new GameState()
		def result = GameController.buildAction(g, "EndTurn")
		
		assertNotNull result
		assertEquals result.getClass(), EndTurn.class
	}
}

