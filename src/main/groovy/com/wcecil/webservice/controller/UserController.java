package com.wcecil.webservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wcecil.data.objects.User;
import com.wcecil.data.repositiories.UsersRepository;

//TODO make secure
@RestController
public class UserController {
	@Autowired
	UsersRepository usersRepo;

	
	@RequestMapping("/players/all")
	public List<User> listAll() {
		return usersRepo.listAll();
	}
	
	@RequestMapping("/players/auth")
	public List<User> auth(@RequestParam("username") String userName,
			@RequestParam("password") String password) {
		return usersRepo.auth(userName, password);
	}

	@RequestMapping("/players/new")
	public User createNew(@RequestParam("username") String userName,
			@RequestParam("password") String password,
			@RequestParam(value = "name", required = false) String name) {
		return usersRepo.createNew(userName, password, name);
	}

}
