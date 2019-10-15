package com.platon.statistic;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StatisticToolApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(StatisticToolApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

}
