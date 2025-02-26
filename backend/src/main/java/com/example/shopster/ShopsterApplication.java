package com.example.shopster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories(basePackages = "com.example.repositories")
@ComponentScan(basePackages = {"com.example.entities",
								"com.example.services",
								"com.example.controllers",
								"com.example.config"})
public class ShopsterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopsterApplication.class, args);
	}

}
