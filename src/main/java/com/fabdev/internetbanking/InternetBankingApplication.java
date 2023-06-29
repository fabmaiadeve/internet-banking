package com.fabdev.internetbanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
public class InternetBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternetBankingApplication.class, args);
	}

}
