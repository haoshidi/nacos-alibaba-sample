package com.czscloud.czs.sample.controller;

import com.czscloud.czs.sample.service.OpenFeignTestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
public class OpenFeignTestController {



    @Resource
    private OpenFeignTestService openFeignTestService;

    @RequestMapping("/openFeignTest")
    public String OpenFeignTest(){
        return openFeignTestService.openFeignProviderTest();
    }

}
