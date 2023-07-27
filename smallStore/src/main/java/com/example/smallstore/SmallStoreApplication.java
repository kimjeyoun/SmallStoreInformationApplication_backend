package com.example.smallstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SmallStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmallStoreApplication.class, args);
    }

}
