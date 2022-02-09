package com.czscloud.czs.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@RestController
public class TestController {

    private final String SERVER_URL="http://sentinel-sample-provider";
    @Resource
    RestTemplate restTemplate;
    @RequestMapping("test")
    public String test(){
        return restTemplate.getForObject(SERVER_URL+"/test",String.class);
    }
    @RequestMapping("/sentinelTest")
    public String sentinelTest(){
        return "TestController#sentinelTest "+RandomUtils.nextInt(0,1000);
    }
}
