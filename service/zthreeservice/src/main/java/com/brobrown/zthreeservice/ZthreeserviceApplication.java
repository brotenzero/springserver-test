package com.brobrown.zthreeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
public class ZthreeserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZthreeserviceApplication.class, args);
	}

}
