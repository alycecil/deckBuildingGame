import com.wcecil.actions.core.BuyCard
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
void playAll(GameState g) {
	while(!g.currentPlayer.hand.isEmpty()) {
		Card c = g.currentPlayer.hand.get(0)

		GameController.doAction(g, new PlayCard(card:c, sourcePlayer:g.currentPlayer))
	}
}
void buyAll(GameState g) {
	while(!g.available.isEmpty()){
		Card c = g.available.get(0);
		GameController.doAction(g, new BuyCard(card:c, sourcePlayer:g.currentPlayer))
	}
}

GameState g = new GameState()

GameController.doAction(g, new LoadGame())

printState(g)

playAll(g)

printState(g)

GameController.doAction(g, new EndTurn())

printState(g)

playAll(g)

g.currentPlayer.money+= 100

buyAll(g)

printState(g)

g.audit.each{ println it }
