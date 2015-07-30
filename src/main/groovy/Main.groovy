import com.wcecil.actions.initial.LoadGame
import com.wcecil.beans.GameState
import com.wcecil.core.GameController;


GameState g = new GameState()

GameController.doAction(g, new LoadGame())

g.audit.each{
	println it
}

