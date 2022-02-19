package com.czscloud.czs.stream.listener;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ConsumerListener {
    @StreamListener("input1")
    public void input1Consumer(String message){
        System.out.println(" input1Consumer received" + message);
        int i = 1/0;
    }
    @StreamListener("input1")
    public void input1ConsumerMessage(Message<String> message){
        String payload = message.getPayload();
        MessageHeaders messageHeaders = message.getHeaders();
        System.out.println(" input1ConsumerMessage -消息内容：" + payload + " 消息头:" + messageHeaders);
    }
    @StreamListener(value="input1",condition = "headers['custom-header']=='customHeader'")
    public void input1ConsumerCondition(String message){
        System.out.println(" inputConsumerCondition received "+ message);
    }
    @StreamListener("input2")
    public void receiveTransactionalMsg(String transactionMsg){
        try{
            System.out.println("receiveTransactionalMsg msg" + transactionMsg+ "data:" +new Date());
        }catch (Exception e){

        }
    }
}
