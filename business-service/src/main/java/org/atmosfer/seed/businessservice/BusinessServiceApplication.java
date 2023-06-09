package org.atmosfer.seed.businessservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger2
public class BusinessServiceApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "business-service");
		SpringApplication.run(BusinessServiceApplication.class, args);
	}

}
