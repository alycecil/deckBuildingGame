package com.wcecil.webservice.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wcecil.beans.dto.User;
import com.wcecil.beans.dto.UserToken;
import com.wcecil.common.settings.ApplicationComponent;
import com.wcecil.data.repositiories.UsersRepository;
import com.wcecil.webservice.service.AuthenticationService;
import com.wcecil.websocket.messanger.MessangerService;

@RestController
public class UserController {
	private @Autowired UsersRepository usersRepo;
	private @Autowired ApplicationComponent context;
	private @Autowired AuthenticationService authService;
	private @Autowired MessangerService messageService;

	@RequestMapping("/players/auth")
	public User auth(@RequestParam("username") String userName,
			@RequestParam("password") String password,
			HttpServletResponse response) throws IOException {
		User user = usersRepo.auth(userName, password);

		if (user == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Not Authorized");
		}

		return user;
	}

	@RequestMapping("/players/token")
	public UserToken authToken(@RequestParam("username") String userName,
			@RequestParam("password") String password,
			HttpServletResponse response) throws IOException {
		User user = usersRepo.auth(userName, password);
		if (user == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Not Authorized");
		}
		return usersRepo.createTokenForUserId(user.getId());
	}

	@RequestMapping("/players/me")
	public User authToken(@RequestParam("token") String token) {
		String userId = authService.getUserFromToken(token);
		User user = usersRepo.getUser(userId);
		return user;
	}

	@RequestMapping("/players/new")
	public User createNew(@RequestParam("username") String userName,
			@RequestParam("password") String password,
			@RequestParam(value = "name", required = false) String name) {
		return usersRepo.createNew(userName, password, name);
	}
}
