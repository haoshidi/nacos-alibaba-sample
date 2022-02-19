package com.czscloud.czs.consumer.sample.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigController {
    @Value("${custom.info}")
    private String config;

    @RequestMapping("/getConfig")
    public String getConfig(){
        return config;
    }
}
