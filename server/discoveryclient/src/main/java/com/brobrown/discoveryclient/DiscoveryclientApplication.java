package com.brobrown.discoveryclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DiscoveryclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryclientApplication.class, args);
	}

}
