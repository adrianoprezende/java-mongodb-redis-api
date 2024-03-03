package com.rezende.javamongodbredisapi;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

@TestConfiguration
public class ApplicationTests {

	@Bean
	public Clock clock() {
		return Clock.fixed(Instant.parse("2001-01-20T08:30:00.00Z"), ZoneOffset.UTC);
	}

}
