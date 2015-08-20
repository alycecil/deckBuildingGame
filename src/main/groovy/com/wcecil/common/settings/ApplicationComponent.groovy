package com.wcecil.common.settings

import groovy.transform.CompileStatic;

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.*

@Component
@CompileStatic
class ApplicationComponent {

	@Value('${mongo.host}')
	String mongoHost
	
	@Value('${mongo.port}')
	Integer mongoPort
	
	@Value('${mongo.account}')
	String mongoUser
	
	
	@Value('${mongo.password}')
	String mongoPass
	
	
	@Value('${mongo.dbname}')
	String mongoDB
	
	
	@Value('${allow.debug}')
	Boolean allowDebug
}
