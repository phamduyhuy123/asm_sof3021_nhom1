package com.nhom2.asmsof3021;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories
public class AsmSof3021Application {

    public static void main(String[] args) {
        SpringApplication.run(AsmSof3021Application.class, args);
    }

}
