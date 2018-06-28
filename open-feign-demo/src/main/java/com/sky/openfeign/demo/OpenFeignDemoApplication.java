package com.sky.openfeign.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableAutoConfiguration
@EnableFeignClients(value = "com.sky.openfeign.demo.feign")
public class OpenFeignDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenFeignDemoApplication.class, args);
	}
}
