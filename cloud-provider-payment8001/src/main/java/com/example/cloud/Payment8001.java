package com.example.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.cloud.**.dao")
public class Payment8001 {
    public static void main(String[] args) {
        SpringApplication.run(Payment8001.class,args);
    }
}
