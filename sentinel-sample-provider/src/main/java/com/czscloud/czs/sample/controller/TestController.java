package com.czscloud.czs.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @RequestMapping("test")
    public String test(){
        return "Sentinel provider sample test() "+RandomUtils.nextInt(0,1000);
    }
}
