package com.ocado.demo.tenant;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class TenantMessageProcessor {
    private static final String TENANT_MESSAGE_ATTRIBUTE_NAME = "tenantId";

    public <T> boolean isValid(Message<T> message) {
        return message.getHeaders().containsKey(TENANT_MESSAGE_ATTRIBUTE_NAME);
    }

    public <T> String extract(Message<T> message) {
        return message.getHeaders().get(TENANT_MESSAGE_ATTRIBUTE_NAME, String.class);
    }
}
