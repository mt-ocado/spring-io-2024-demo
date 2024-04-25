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
    //<editor-fold desc="// private fields">
    private static final Logger logger = LoggerFactory.getLogger(TenantIdMessagePostProcessor.class);
    private final TenantContext tenantContext;
    //</editor-fold>

    //<editor-fold desc="// default constructor">
    public TenantIdMessagePostProcessor(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }
    //</editor-fold>

    @Override
    public Message<?> postProcessMessage(Message<?> message) {
        //<editor-fold desc="// logs">
        logger.info("b. Set tenant ID \"{}\" in message attributes", tenantContext.get());
        //</editor-fold>

        var tid = tenantContext.get();
        return MessageBuilder.fromMessage(message)
                .setHeader("tenantId", tid)
                .build();
    }
}
