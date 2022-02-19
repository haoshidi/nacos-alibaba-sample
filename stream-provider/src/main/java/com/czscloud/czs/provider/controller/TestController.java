package com.czscloud.czs.provider.controller;

import com.czscloud.czs.provider.service.SendMessageService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private SendMessageService sendMessageService;

    @RequestMapping("/sendMessage")
    public String sendMessage(){
        return sendMessageService.sendMessage();
    }
    @RequestMapping("/sendObjectMessage")
    public String sendObjectMessage(){
        return sendMessageService.sendObjectMessage();
    }
    @RequestMapping("/sendConditionMessage")
    public String sendConditionMessage(){
        return sendMessageService.sendConditionMessage();
    }
    @RequestMapping("/sendTagsMessage")
    public String sendTagsMessage(){
        return sendMessageService.sendTagsMessage();
    }

    @RequestMapping("/sendTransactionMessage")
    public String sendTransactionMessage(){
        return sendMessageService.sendTransactionMessage();
    }
}
