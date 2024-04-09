package com.ocado.demo.tenant.sqs;

import com.ocado.demo.sqs.SqsListenerEffect;
import com.ocado.demo.tenant.TenantContext;
import com.ocado.demo.tenant.TenantMessageProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

@Slf4j
@RequiredArgsConstructor
public class TenantIdSqsListenerEffect implements SqsListenerEffect {
    private final TenantContext tenantContext;
    private final TenantMessageProcessor tenantMessageProcessor;

    @Override
    public void before(Message<?> message) {
        log.info("Before");
        if (isValid(message)) {
            var tid = this.tenantMessageProcessor.get(message);
            tenantContext.setId(tid);
        } else {
            handleInvalid(message);
        }
    }

    @Override
    public void after(Message<?> message) {
        log.info("After");
        tenantContext.clearId();
    }

    private void handleInvalid(Message<?> message) {
        log.error("Invalid message");
    }

    private boolean isValid(Message<?> message) {
        return true;
    }
}
