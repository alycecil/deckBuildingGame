package com.wcecil.common.settings

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.*

@Component
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
}
