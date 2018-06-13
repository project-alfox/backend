package com.ace.alfox;

import com.ace.alfox.lib.ActionDatabase;
import com.ace.alfox.lib.Database;
import com.ace.alfox.lib.IAction;
import com.ace.alfox.lib.PlayerAction;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import java.io.IOException;

@EnableWebMvc
@SpringBootApplication
@EnableAutoConfiguration
public class AlfoxApplication implements WebMvcConfigurer {

	public AlfoxApplication() throws IOException {
		Database.get(Database.Keys.Character);

		try {
			ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
			scanner.addIncludeFilter(new AnnotationTypeFilter(PlayerAction.class));
			for (BeanDefinition bd : scanner.findCandidateComponents("com.ace.alfox.game")) {
				// use my annotation to get simple name
				String alias = Class.forName(bd.getBeanClassName()).getAnnotation(PlayerAction.class).alias();
				System.out.println(alias);
				Class cl = Class.forName(bd.getBeanClassName());
				ActionDatabase.actions.put(alias, cl);
			}
		} catch (Exception e) {
			System.out.println("Nope");
		}
	}

	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**");
	}

	public static void main(String[] args) {
		SpringApplication.run(AlfoxApplication.class, args);
	}
}
