package com.wcecil.beans.dto;

import groovy.transform.CompileStatic
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
@CompileStatic
public class UserToken {
	@Id
	String token
	
	@Indexed
	String userId
	
	//TODO Date experation
}
