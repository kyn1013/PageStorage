package com.example.PageStorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PageStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(PageStorageApplication.class, args);
	}

}
