package com.sia.taco.cloud.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TacoClientApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(TacoClientApplication.class, args);

        System.exit(SpringApplication.exit(ctx, () -> 0));
    }
}
