package com.wcecil.data.repositiories;

import static org.springframework.data.mongodb.core.query.Criteria.*
import static org.springframework.data.mongodb.core.query.Query.*
import groovy.transform.CompileStatic
import groovy.transform.Synchronized

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

import com.mongodb.WriteResult
import com.wcecil.data.objects.GameSearch

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
	
	@Synchronized
	public GameSearch enterSearch(String userId){
		//TODO MOVE TO REDIS SET
		
		def gs = new GameSearch(userId: userId);
		
		mongoTemplate.save(gs, SEARCH_REPO);
		
		gs
	}
	
	@Synchronized
	public void matchGames(){
		//TODO move to redis
		
		//TODO use RLock
		
		List<GameSearch> list = listAll()
		
		while(list.size()>1){
			GameSearch a, b;
			a = getPlayer(list);
			b = getPlayer(list);
			
			if(!a){
				println 'Unable to find even 1'
			}else if(a&&!b){
				mongoTemplate.save(a, SEARCH_REPO);
				println "re-adding a of $a"
			}else if(a.userId?.equals(b.userId)){ //TODO SOLVED WHEN USING RSET IN FUTURE
				mongoTemplate.save(a, SEARCH_REPO);
				println "Found dupe for ${a.userId} and ${b.userId}"
			}else{
				//TODO match game for a and b
				println "Matched a game for ${a.userId} and ${b.userId}"
			}
			
			list = listAll()
		}
	}

	@Synchronized
	private GameSearch getPlayer(List<GameSearch> list){
		GameSearch gs = null;
		while(gs==null && !list.isEmpty()){
			gs = list.remove(0);
			WriteResult result = mongoTemplate.remove(gs, SEARCH_REPO)
			//TODO Ensure element was removed (ie actually in list to start with; might just depend on lock... 
		}
		
		gs
	}
	

}
