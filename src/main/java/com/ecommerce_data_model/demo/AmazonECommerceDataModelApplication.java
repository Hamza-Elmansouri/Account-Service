package com.ecommerce_data_model.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class AmazonECommerceDataModelApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazonECommerceDataModelApplication.class, args);
	}

}
