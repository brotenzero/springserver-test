package com.brobrown.soccerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SoccerserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoccerserviceApplication.class, args);
	}

}
