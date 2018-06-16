package com.ace.alfox;

import com.ace.alfox.lib.ActionFactory;
import com.ace.alfox.lib.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@SpringBootApplication
@EnableAutoConfiguration
public class AlfoxApplication implements WebMvcConfigurer {

	public AlfoxApplication() throws ClassNotFoundException {
		ActionFactory.findActions("com.ace.alfox.game");
	}

	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**");
	}

	public static void main(String[] args) {
		SpringApplication.run(AlfoxApplication.class, args);
	}
}
