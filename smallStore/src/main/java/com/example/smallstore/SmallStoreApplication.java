package com.example.smallstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SmallStoreApplication {
    // 로컬 환경은 aws 환경이 아니라서 발생하는 에러 해결을 위함
    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }
    public static void main(String[] args) {
        SpringApplication.run(SmallStoreApplication.class, args);
    }

}
