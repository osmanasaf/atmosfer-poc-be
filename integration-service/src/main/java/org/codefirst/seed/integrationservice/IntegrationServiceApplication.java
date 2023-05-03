package org.codefirst.seed.integrationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntegrationServiceApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "integration-service");
		SpringApplication.run(IntegrationServiceApplication.class, args);
	}

}
