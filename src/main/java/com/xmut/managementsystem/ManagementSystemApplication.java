package com.xmut.managementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ManagementSystemApplication {

    public static void main(String[] args){
        SpringApplication.run(ManagementSystemApplication.class, args);
    }

}
