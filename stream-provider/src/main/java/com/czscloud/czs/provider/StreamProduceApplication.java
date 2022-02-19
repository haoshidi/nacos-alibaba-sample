package com.czscloud.czs.provider;

import com.czscloud.czs.provider.source.CustomSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

@Slf4j
@SpringBootApplication
@EnableBinding({CustomSource.class})
@EnableDiscoveryClient
public class StreamProduceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StreamProduceApplication.class,args);
    }
}
