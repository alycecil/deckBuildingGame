package com.wcecil.data.repositiories;

import static org.springframework.data.mongodb.core.query.Criteria.*
import static org.springframework.data.mongodb.core.query.Query.*

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

import com.wcecil.beans.GameState
import com.wcecil.data.objects.GameAudit

@Service
public class AuditRepository {
	private final static String __REPO = "deck.game.audit";
	@Autowired
	MongoTemplate mongoTemplate;

	public List<GameAudit> getAuditsForGame(String gameId){
		mongoTemplate.find(query(where("gameId").is(gameId)), GameAudit.class, __REPO);
	}

	def saveAudit(GameAudit ga){
		mongoTemplate.save(ga, __REPO)
	}
	
	def flushAudits(GameState g){
		List<GameAudit> gas = g.audit
		g.audit = []
		
		
		gas.each{
			GameAudit ga ->
			saveAudit(ga);
		}
	}
}
