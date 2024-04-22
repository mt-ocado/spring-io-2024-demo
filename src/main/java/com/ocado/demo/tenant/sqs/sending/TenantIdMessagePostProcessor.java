package com.ocado.demo.tenant.sqs.sending;

import com.ocado.demo.tenant.TenantContext;
import com.ocado.demo.tenant.TenantMessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

@Component
public class TenantIdMessagePostProcessor implements MessagePostProcessor {
    private static final Logger log = LoggerFactory.getLogger(TenantIdMessagePostProcessor.class);
    private final TenantContext tenantContext;
    private final TenantMessageProcessor tenantMessageProcessor;

    public TenantIdMessagePostProcessor(TenantContext tenantContext, TenantMessageProcessor tenantMessageProcessor) {
        this.tenantContext = tenantContext;
        this.tenantMessageProcessor = tenantMessageProcessor;
    }

    @Override
    public Message<?> postProcessMessage(Message<?> message) {
        var tid = tenantContext.getId();
        log.info("Setting tenant ID {} in message attributes", tid);
        return tenantMessageProcessor.set(message, tid);
    }
}
