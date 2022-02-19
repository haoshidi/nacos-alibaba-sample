package com.czscloud.czs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Service;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class JwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class,args);
    }
}
