package com.ocado.demo.tenant;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class TenantMessageProcessor {
    private static final String TENANT_MESSAGE_ATTRIBUTE_NAME = "tenantId";

    public <T> boolean isValid(Message<T> message) {
        return message.getHeaders().containsKey(TENANT_MESSAGE_ATTRIBUTE_NAME);
    }

    public <T> Message<T> set(Message<T> message, String tenantId) {
        return MessageBuilder.fromMessage(message)
                .setHeader(TENANT_MESSAGE_ATTRIBUTE_NAME, tenantId)
                .build();
    }

    public <T> String get(Message<T> message) {
        return message.getHeaders().get(TENANT_MESSAGE_ATTRIBUTE_NAME, String.class);
    }
}
