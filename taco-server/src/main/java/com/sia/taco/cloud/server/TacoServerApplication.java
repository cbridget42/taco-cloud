package com.sia.taco.cloud.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.sia.taco.cloud.api.entity")
public class TacoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoServerApplication.class, args);
    }

}
