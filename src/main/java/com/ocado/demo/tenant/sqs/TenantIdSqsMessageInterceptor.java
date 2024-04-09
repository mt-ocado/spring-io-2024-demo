package com.ocado.demo.tenant.sqs;

import com.ocado.demo.tenant.TenantContext;
import com.ocado.demo.tenant.TenantMessageProcessor;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

@Slf4j
@RequiredArgsConstructor
public class TenantIdSqsMessageInterceptor implements MessageInterceptor<Object> { // should be Object, otherwise won't be autowired in Spring Cloud AWS autoconfiguration
    private final TenantContext tenantContext;
    private final TenantMessageProcessor tenantMessageProcessor;

    @Override
    public Message<Object> intercept(Message<Object> message) {
        log.info("Intercept");
        if (isValid(message)) {
            var tid = this.tenantMessageProcessor.get(message);
            tenantContext.setId(tid);
        } else {
            handleInvalid(message);
        }
        return message;
    }

    @Override
    public void afterProcessing(Message<Object> message, Throwable t) {
        log.info("After processing");
        tenantContext.clearId();
    }

    private void handleInvalid(Message<?> message) {
        log.error("Invalid message");
    }

    private boolean isValid(Message<?> message) {
        // validate message
        return true;
    }
}
