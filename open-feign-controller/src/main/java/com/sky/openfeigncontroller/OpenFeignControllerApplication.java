package com.sky.openfeigncontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class OpenFeignControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenFeignControllerApplication.class, args);
    }

}
