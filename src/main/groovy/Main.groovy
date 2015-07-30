import com.wcecil.actions.core.EndTurn
import com.wcecil.actions.core.PlayCard
import com.wcecil.actions.initial.LoadGame
import com.wcecil.beans.GameState
import com.wcecil.beans.gameobjects.Card
import com.wcecil.core.GameController

void printState(GameState g){
	println """Current Game State
Tic : ${g.ticCount.get()}
Active Player : ${g.currentPlayer}
Available : ${g.available}
"""
}

GameState g = new GameState()

GameController.doAction(g, new LoadGame())

printState(g)

while(!g.currentPlayer.hand.isEmpty()) {
	Card c = g.currentPlayer.hand.get(0)
	
	GameController.doAction(g, new PlayCard(card:c, sourcePlayer:g.currentPlayer))
}

printState(g)

GameController.doAction(g, new EndTurn())

printState(g)

g.audit.each{ println it }
