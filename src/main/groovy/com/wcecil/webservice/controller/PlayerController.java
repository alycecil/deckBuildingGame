package com.wcecil.webservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wcecil.beans.Player;
import com.wcecil.data.repositiories.PlayerRepository;

@RestController
public class PlayerController {
//	@Autowired
//	PlayerRepository playerRepo;

	@Autowired MongoTemplate mongoTemplate;
	
	@RequestMapping("/players/all")
	public List<Player> listAll() {
		return mongoTemplate.findAll(Player.class);
	}

	@RequestMapping("/players/new")
	public Player createNew(@RequestParam("username") String userName,
			@RequestParam("password") String password,
			@RequestParam(value = "name", required = false) String name) {
		if(name==null){
			name = userName;
		}
		Player p = new Player();
		p.setLogin(userName);
		p.setPassword(password);
		p.setName(name);
		
		mongoTemplate.save(p);
		
		return p;
	}

}
