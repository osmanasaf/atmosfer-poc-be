package org.atmosfer.seed.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class UserServiceApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "user-service");
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
