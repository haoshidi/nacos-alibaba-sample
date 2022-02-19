package com.czscloud.czs.provider.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CustomSource {
    @Output("output1")
    MessageChannel output1();
    @Output("output2")
    MessageChannel output2();
}
