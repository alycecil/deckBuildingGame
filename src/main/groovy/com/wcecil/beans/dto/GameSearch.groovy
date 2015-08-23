package com.wcecil.beans.dto

import groovy.transform.Canonical;
import groovy.transform.CompileStatic;

import javax.validation.constraints.NotNull

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
@Canonical
@CompileStatic
class GameSearch {
	
	@Id
	String id
	
	@NotNull
	@Indexed(unique=true)
	String userId
}
