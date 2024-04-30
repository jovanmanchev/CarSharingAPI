package com.carsharing.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.carsharing.app.model")
public class CarSharingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarSharingApplication.class, args);
	}

}
