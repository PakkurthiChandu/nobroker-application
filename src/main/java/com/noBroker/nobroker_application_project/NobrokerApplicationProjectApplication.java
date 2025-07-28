package com.noBroker.nobroker_application_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NobrokerApplicationProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NobrokerApplicationProjectApplication.class, args);
	}

}
