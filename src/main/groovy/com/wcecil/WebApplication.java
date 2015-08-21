package com.wcecil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.wcecil.common.settings.ApplicationComponent;
import com.wcecil.configuration.MongoConfiguration;
import com.wcecil.configuration.SchedulerConfig;

@SpringBootApplication
@EnableMongoRepositories
@Import({MongoConfiguration.class, SchedulerConfig.class})
public class WebApplication extends WebMvcConfigurerAdapter {

	@Autowired
	ApplicationComponent context;

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/static/web/", "classpath:/static/web/");
	}
}
