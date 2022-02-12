package com.czscloud.czs.sample.controller;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenFeignController {
    @Value("${server.port}")
    private String server_port;

    @RequestMapping("/openFeignProviderTest")
    public String openFeignProviderTest(){
        //int a = 1 / 0; //除数不能为0，此处必报错
        //return "OpenFeignTestController#openFeignProviderTest" + RandomUtils.nextInt(0,1000);
//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return "OpenFeignTestController#openFeignProviderTest" + server_port;
    }
}
