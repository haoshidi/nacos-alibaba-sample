package com.czscloud.czs.stream;

import com.czscloud.czs.stream.sink.CustomSink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding( {CustomSink.class} )
@EnableDiscoveryClient
public class StreamConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(StreamConsumerApplication.class,args);
    }
}
