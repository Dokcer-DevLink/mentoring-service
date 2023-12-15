package com.goorm.devlink.mentoringservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MentoringServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MentoringServiceApplication.class, args);
	}

}
