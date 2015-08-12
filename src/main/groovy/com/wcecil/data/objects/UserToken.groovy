package com.wcecil.data.objects;

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
public class UserToken {
	@Id
	String token
	
	@Indexed
	String userId
	
	//TODO Date experation
}
