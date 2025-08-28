package com.eminpolat.sensorfileproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SensorFileProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensorFileProjectApplication.class, args);
    }

}
