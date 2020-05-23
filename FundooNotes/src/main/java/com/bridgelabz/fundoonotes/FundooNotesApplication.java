package com.bridgelabz.fundoonotes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class FundooNotesApplication {

	
	private static final Logger log = LoggerFactory.getLogger(FundooNotesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FundooNotesApplication.class, args);
		log.info("Hiii There this is Spring Boot App....!");
	}

}
