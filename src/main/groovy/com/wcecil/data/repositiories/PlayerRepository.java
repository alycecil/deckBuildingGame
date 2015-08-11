package com.wcecil.data.repositiories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.wcecil.beans.Player;

public interface PlayerRepository {}
//extends MongoRepository<Player, String>
//{
//	@Query("{ 'login' : ?0, 'password' : ?1 }")
//	List<Player> findByThePersonsFirstname(String login, String password);
//}
