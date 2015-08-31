package com.wcecil.webservice.controller;

import groovy.transform.CompileStatic;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController

import com.wcecil.beans.dto.GameState
import com.wcecil.beans.dto.User
import com.wcecil.beans.gameobjects.Player
import com.wcecil.common.settings.ApplicationComponent
import com.wcecil.data.repositiories.AuditRepository
import com.wcecil.data.repositiories.GameRepository
import com.wcecil.data.repositiories.GameSearchRepository
import com.wcecil.data.repositiories.UsersRepository
import com.wcecil.game.core.GameController
import com.wcecil.io.util.MaskingHelper;
import com.wcecil.webservice.service.AuthenticationService
import com.wcecil.websocket.messanger.MessangerService

@RestController
@RequestMapping("/admin")
@CompileStatic
public class AdminController {

	private @Autowired ApplicationComponent context;
	private @Autowired UsersRepository usersRepo;
	private @Autowired GameRepository gamesRepo;
	private @Autowired AuditRepository auditRepo;
	private @Autowired GameSearchRepository gameSearchRepo;
	private @Autowired AuthenticationService authService;
	private @Autowired MessangerService messageService;
	private @Autowired MessangerService messangerService;
	private @Autowired GameController gameController;
	private @Autowired MaskingHelper maskingHelper;

	@RequestMapping("/players/all")
	public List<User> listAll(HttpServletResponse response) throws IOException {
		if (!context.getAllowDebug()) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Not Authorized");
			return null;
		}
		return usersRepo.listAll();
	}

	@RequestMapping(value = "/quit/all")
	public Boolean quitAllGames(
			HttpServletResponse response) throws IOException {
		if (!context.getAllowDebug()) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Not Authorized");
			return null;
		}

		usersRepo.listAll().each { User u ->
			u.getGames()?.each{ String gameId->
				GameState g = gamesRepo.getGame(gameId, false);
				if(g){
					g?.players?.each {  Player p ->
						if(p?.userId){
							messageService.alertUser(p.userId, 'You have been kicked from a game.');
						}
						p.userId = null;
					}
					
					gamesRepo.delete(g);
				}
			}

			u.setGames([]);
			usersRepo.saveUser(u);
		}

		return true;
	}

	@RequestMapping("/players/add/game")
	public User createNew(@RequestParam("userId") String userId,
			@RequestParam("gameId") String gameId, HttpServletResponse response)
	throws IOException {
		if (!context.getAllowDebug()) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Not Authorized");
			return null;
		}
		return usersRepo.addGameToUser(userId, gameId);
	}

	@RequestMapping("/players/message/send")
	public Boolean sendMessage(@RequestParam("userId") String userId,
			@RequestParam("message") String message, HttpServletResponse response)
	throws IOException {
		if (!context.getAllowDebug()) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Not Authorized");
			return null;
		}

		messageService.alertUser(userId, message);

		return true;
	}
}
