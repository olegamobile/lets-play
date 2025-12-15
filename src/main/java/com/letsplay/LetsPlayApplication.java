package com.letsplay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the main class for the Spring Boot application.
 * @SpringBootApplication is a convenience annotation that adds all of the following:
 *  - @Configuration: Tags the class as a source of bean definitions for the application context.
 *  - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
 *  - @ComponentScan: Tells Spring to look for other components, configurations, and services in the 'com.letsplay' package, allowing it to find and register the controllers, services, etc.
 */
@SpringBootApplication
public class LetsPlayApplication {

	/**
	 * The main method, which serves as the entry point for the application.
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(LetsPlayApplication.class, args);
	}

}
