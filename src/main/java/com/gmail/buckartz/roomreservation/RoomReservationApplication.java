package com.gmail.buckartz.roomreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile({"dev", "test"})
public class RoomReservationApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(RoomReservationApplication.class);
		app.setAdditionalProfiles("dev");
		app.run(args);
	}
}
