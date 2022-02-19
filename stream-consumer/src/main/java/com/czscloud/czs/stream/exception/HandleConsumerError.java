package com.czscloud.czs.stream.exception;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

@Component
public class HandleConsumerError {
    @ServiceActivator(inputChannel = "test-topic.test-group.errors") //局部异常处理
    public void handleError(ErrorMessage message){
        Throwable throwable = message.getPayload();
        System.out.println("截获异常：" + throwable.getMessage());
        System.out.println("原始消息：" + new String((byte[])((MessagingException)throwable).getFailedMessage().getPayload()));
    }
    @StreamListener("errorChannel")
    public void globalHandleError(ErrorMessage message){
        Throwable throwable = message.getPayload();
        System.out.println("全局异常截获 - 截获异常：" + throwable.getMessage());
        System.out.println("全局异常截获 - 原始消息：" + new String((byte[])((MessagingException)throwable).getFailedMessage().getPayload()));
    }
}
