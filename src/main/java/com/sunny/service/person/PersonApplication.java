package com.sunny.service.person;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.sunny.service.person")
public class PersonApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersonApplication.class,args);
    }
}