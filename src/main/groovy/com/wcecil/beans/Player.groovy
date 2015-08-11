package com.wcecil.beans

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Player {
	
	@Id
	String id
	
	@NotNull
	@Indexed(unique=true)
	String password
	
	@Indexed
	String login
	
	String name
}
