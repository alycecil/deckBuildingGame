package com.wcecil.data.objects;

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document

@Document
public class GameAudit implements Comparable<GameAudit>{
	@Id
	String id

	@Indexed
	String gameId

	String playerId

	Long order
	
	String message

	@Override
	public int compareTo(GameAudit o) {
		if(!o || !order || !o.order){
			return 0
		}
		
		order.compareTo(o.getOrder())
	}
}
