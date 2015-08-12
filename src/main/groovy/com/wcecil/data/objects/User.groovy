package com.wcecil.data.objects

import javax.validation.constraints.NotNull

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
class User {
	
	@Id
	String id
	
	@NotNull
	@Indexed
	String password
	
	@Indexed(unique=true)
	String login
	
	String name
	
	List<String> games;
}
