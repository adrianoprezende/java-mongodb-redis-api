package com.rezende.javamongodbredisapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.Clock;
import java.time.ZoneOffset;

@EnableCaching
@EnableMongoRepositories
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Bean
	public Clock clock() {
		return Clock.system(ZoneOffset.UTC);
	}

}
