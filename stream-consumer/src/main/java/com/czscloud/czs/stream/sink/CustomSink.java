package com.czscloud.czs.stream.sink;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomSink {
    @Input("input1")
    SubscribableChannel input1();
    @Input("input2")
    SubscribableChannel input2();
}
