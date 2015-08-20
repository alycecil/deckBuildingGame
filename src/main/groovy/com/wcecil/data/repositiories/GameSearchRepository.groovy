package com.wcecil.data.repositiories;

import static org.springframework.data.mongodb.core.query.Criteria.*
import static org.springframework.data.mongodb.core.query.Query.*
import groovy.transform.CompileStatic;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

import com.wcecil.data.objects.GameSearch;
import com.wcecil.data.objects.User
import com.wcecil.data.objects.UserToken

@Service
@CompileStatic
public class GameSearchRepository {
	private final static String SEARCH_REPO = "deck.game.search";

	@Autowired
	MongoTemplate mongoTemplate;

	public List<GameSearch> listAll() {
		def result = mongoTemplate.findAll(GameSearch.class, SEARCH_REPO);

		result
	}
	
	
	public GameSearch enterSearch(String userId){
		def gs = new GameSearch(userId: userId);
		
		mongoTemplate.save(gs, SEARCH_REPO);
		
		gs
	}

	

}
