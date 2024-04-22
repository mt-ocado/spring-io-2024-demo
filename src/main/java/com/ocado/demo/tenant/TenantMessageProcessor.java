package com.ocado.demo.tenant;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class TenantMessageProcessor {
    private static final String TENANT_MESSAGE_ATTRIBUTE_NAME = "tenantId";

    public <T> Message<T> setInAttributes(Message<T> message, String tenantId) {
        return MessageBuilder.fromMessage(message)
                .setHeader(TENANT_MESSAGE_ATTRIBUTE_NAME, tenantId)
                .build();
    }

    public <T> String extractFromAttributes(Message<T> message) {
        return message.getHeaders().get(TENANT_MESSAGE_ATTRIBUTE_NAME, String.class);
    }
}
