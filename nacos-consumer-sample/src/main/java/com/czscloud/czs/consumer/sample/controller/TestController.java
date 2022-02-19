package com.czscloud.czs.consumer.sample.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class TestController {

    private final String SERVER_URL= "http://nacos-provider-sample";
    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/test")
    public String test(){
        return restTemplate.getForObject(SERVER_URL+"/test",String.class);
    }
}
