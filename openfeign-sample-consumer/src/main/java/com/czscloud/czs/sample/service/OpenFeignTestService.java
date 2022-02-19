package com.czscloud.czs.sample.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "${privoder.name}",fallback = OpenFeignTestServiceFallback.class)
public interface OpenFeignTestService {
    @RequestMapping(value = "/openFeignProviderTest",method = RequestMethod.GET)
    public String openFeignProviderTest();
}
