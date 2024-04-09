package com.ocado.demo.sqs;

import org.springframework.messaging.Message;

public interface SqsListenerEffect {
    void before(Message<?> message);

    void after(Message<?> message);
}
