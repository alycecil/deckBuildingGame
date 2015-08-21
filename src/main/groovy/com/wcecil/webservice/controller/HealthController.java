package com.wcecil.webservice.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wcecil.common.settings.ApplicationComponent;

@RestController
public class HealthController {
	@Autowired ApplicationComponent context;
	
    @RequestMapping("/echo")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="Echo") String name) {
        return "Echo, "+name;
    }
    
    @RequestMapping("/mongo/server")
    public String settings() {
        return context.getMongoHost();
    }

    @RequestMapping({"/", "/index", "/index.html"})
    public String root(
			HttpServletResponse response) throws IOException {
    	response.sendRedirect("/resources/app.html");
        return "forward:/resources/app.html";
    }
}
