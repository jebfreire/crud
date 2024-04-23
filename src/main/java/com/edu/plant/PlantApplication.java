package com.edu.plant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.edu.plant.domain.entities")
public class PlantApplication {
	private static final Logger logger = LogManager.getLogger(PlantApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PlantApplication.class, args);
	}
}
