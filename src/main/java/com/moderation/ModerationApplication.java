package com.moderation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ModerationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModerationApplication.class, args);
	}

}
