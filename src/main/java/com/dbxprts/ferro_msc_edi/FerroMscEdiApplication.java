package com.dbxprts.ferro_msc_edi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FerroMscEdiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FerroMscEdiApplication.class, args);
	}

}
