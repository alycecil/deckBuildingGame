package com.wcecil.webservice.controller;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wcecil.actions.initial.LoadGame;
import com.wcecil.beans.GameState;
import com.wcecil.core.GameController;

@RestController
@RequestMapping("/game")
public class GameInfoController {

	Map<Long, GameState> games = new Hashtable<>();

	@RequestMapping(value = "/get", method = { RequestMethod.GET })
	public GameState getGame(@RequestParam(value = "id") Long id) {

		return games.get(id);
	}
	
	@RequestMapping(value = "/new", method = { RequestMethod.GET,RequestMethod.POST })
	public GameState newGame() {

		GameState g = new GameState();
		GameController.doAction(g, new LoadGame());
		
		games.put(g.getId(), g);
		
		return g;
	}

}
