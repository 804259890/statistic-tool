package com.platon.statistic;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StatisticApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(StatisticApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

}
