package com.czscloud.czs.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class SentinelProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelProviderApplication.class,args);
    }
}
