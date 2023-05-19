package com.project.transferapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TransferApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransferApiApplication.class, args);
    }

}
