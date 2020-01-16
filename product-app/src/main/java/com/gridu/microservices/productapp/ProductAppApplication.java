package com.gridu.microservices.productapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication
public class ProductAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductAppApplication.class, args);
	}

}
