package com.czscloud.czs.sample.A.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {
    @Value("${server.port}")
    private String port;

    @RequestMapping("/test")
    public String test(){
        return "hello, test :"+port;
    }
}
