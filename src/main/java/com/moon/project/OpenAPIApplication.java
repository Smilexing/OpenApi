package com.moon.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.moon.project.mapper")
public class OpenAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenAPIApplication.class, args);
    }

}
