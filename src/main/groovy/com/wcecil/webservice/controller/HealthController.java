package com.wcecil.webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wcecil.common.settings.ApplicationComponent;

@RestController
public class HealthController {
	@Autowired ApplicationComponent context;
	
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        return "Hello, "+name;
    }
    
    @RequestMapping("/mongo/server")
    public String settings() {
        return context.getMongoHost();
    }

}
