package com.czscloud.czs.provider.service;

import com.czscloud.czs.provider.entity.Users;
import com.czscloud.czs.provider.source.CustomSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@Service
public class SendMessageService {
    @Resource
    private CustomSource customSource;

    public String sendMessage(){
        String payload = "发送简单字符串消息的方法" + RandomUtils.nextInt(0,500);
        customSource.output1().send(MessageBuilder.withPayload(payload).build());
        return payload;
    }
    public String sendObjectMessage(){
        Users users = new Users();
        users.setId(RandomUtils.nextInt(0,500));
        users.setName("zhangsan");
        Message message = MessageBuilder.withPayload(users).build();
        customSource.output1().send(message);
        return "用户id：" + users.getId() +"用户名称："+users.getName();
    }

    public String sendConditionMessage() {
        String payload = "发送有请求头字符串测试消息" + RandomUtils.nextInt(0,500);
        customSource.output1().send(MessageBuilder.withPayload(payload).setHeader("custom-header","customHeader").build());
        return payload;
    }
    public String sendTagsMessage(){
        String payload = "发送有tags测试消息" + RandomUtils.nextInt(0,500);
        customSource.output1().send(MessageBuilder.withPayload(payload).setHeader(RocketMQHeaders.TAGS,"test").build());
        return payload;
    }

    public String sendTransactionMessage() {
        String uuid = UUID.randomUUID().toString();
        String payload = "发送事务测试消息"+uuid;
        customSource.output2().send(MessageBuilder.withPayload(payload).setHeader(RocketMQHeaders.TRANSACTION_ID,uuid).build());
        return payload;
    }
}
