package com.mirishop.productmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProductmanagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductmanagementServiceApplication.class, args);
	}

}
