package com.ocado.demo.sqs.sending;

import com.ocado.demo.tenant.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class TenantIdMessagePostProcessor implements MessagePostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(TenantIdMessagePostProcessor.class);
    private final TenantContext tenantContext;

    public TenantIdMessagePostProcessor(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    @Override
    public Message<?> postProcessMessage(Message<?> message) {
        var tid = tenantContext.get();
        logger.info("Setting tenant ID \"{}\" in message attributes", tid);
        return MessageBuilder.fromMessage(message)
                .setHeader("tenantId", tid)
                .build();
    }
}
