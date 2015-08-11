package com.wcecil.data.objects

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
class UserGameRef {
	
	@Id
	String id
	
	
	@Indexed
	String userId
	
	@Indexed
	String gameId
	
}
