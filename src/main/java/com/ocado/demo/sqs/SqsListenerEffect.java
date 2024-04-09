package com.ocado.demo.sqs;

import org.springframework.messaging.Message;

public interface SqsListenerEffect {
    void before(Message<?> message);

    default void after(Message<?> message) {
        //do nothing
    }
}
