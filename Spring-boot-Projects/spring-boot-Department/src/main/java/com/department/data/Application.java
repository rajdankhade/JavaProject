package com.department.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
* This is a spring boot start application program for 
* Department application.
*
* @author  Rajkumar
* @version 1.0
*  
*/

@Configuration
@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
@EnableSwagger2
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
