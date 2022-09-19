package com.bridgelabz.FundooNotes_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class FundooNotesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooNotesServiceApplication.class, args);
		System.out.println("Welcome to Fundoo Notes Service !!");
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
