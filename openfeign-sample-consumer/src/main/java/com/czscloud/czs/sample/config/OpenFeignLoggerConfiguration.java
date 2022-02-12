package com.czscloud.czs.sample.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeignLoggerConfiguration {
    @Bean
    public Logger.Level OpenFeignLoggerLevel(){
        return Logger.Level.FULL; //日志的最全级别
        // NONE:无记录，默认 BASIC:只记录请求方法和url及响应状态代码和执行时间 HEADERS:只记基本信息及请求和响应头 FULL: 记录请求和响应头文件，正文和元数据，信息最全
    }
}
