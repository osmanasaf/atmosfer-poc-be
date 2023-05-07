package org.atmosfer.seed.logservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogServiceApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "log-service");
		SpringApplication.run(LogServiceApplication.class, args);
	}

}
