package org.atmosfer.seed.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "api-gateway");
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
