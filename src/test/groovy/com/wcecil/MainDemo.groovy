package com.wcecil


import org.junit.Test

import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Card
import com.wcecil.game.actions.core.BuyCard
import com.wcecil.game.actions.core.EndTurn
import com.wcecil.game.actions.core.PlayCard
import com.wcecil.game.actions.initial.LoadGame
import com.wcecil.game.core.GameController

public class MainDemo {
	
	GameController gc = new GameController();

	void printState(GameState g){
		println """Current Game State
Tic : ${g.ticCount.get()}
Active Player : ${g.currentPlayer}
Available : ${g.available}
"""
	}
	void playAll(GameState g) {
		while(!g.currentPlayer.hand.isEmpty()) {
			Card c = g.currentPlayer.hand.get(0)

			gc.doAction(g, new PlayCard(sourceCard:c, sourcePlayer:g.currentPlayer))
		}
	}
	void buyAll(GameState g) {
		while(!g.available.isEmpty()){
			Card c = g.available.get(0);
			gc.doAction(g, new BuyCard(targetCard:c, sourcePlayer:g.currentPlayer))
		}
	}

	@Test
	public void test(){
		GameState g = new GameState()

		gc.doAction(g, new LoadGame())

		printState(g)

		playAll(g)

		printState(g)

		gc.doAction(g, new EndTurn())

		printState(g)

		playAll(g)

		g.currentPlayer.money+= 100

		buyAll(g)

		printState(g)

		(1..10).each{
			gc.doAction(g, new EndTurn())

			printState(g)
		}



		g.audit.each{
			println it
		}
	}
}