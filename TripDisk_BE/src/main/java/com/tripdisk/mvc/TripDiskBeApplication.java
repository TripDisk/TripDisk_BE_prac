package com.tripdisk.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tripdisk.mvc")
public class TripDiskBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripDiskBeApplication.class, args);
	}

}
