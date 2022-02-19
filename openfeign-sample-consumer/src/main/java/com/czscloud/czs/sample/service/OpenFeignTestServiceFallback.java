package com.czscloud.czs.sample.service;

import org.springframework.stereotype.Component;

@Component
public class OpenFeignTestServiceFallback implements OpenFeignTestService{

    @Override
    public String openFeignProviderTest() {
        return "我是兜底方法！";
    }
}
