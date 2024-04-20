package com.ocado.demo.tenant.observability;

import com.ocado.demo.tenant.TenantContext;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

public class ObservabilityMessageInterceptor<T> implements MessageInterceptor<T> {
    private final Logger log = LoggerFactory.getLogger(ObservabilityMessageInterceptor.class);
    private final TenantContext tenantContext;
    private final AccessCounter accessCounter;

    public ObservabilityMessageInterceptor(TenantContext tenantContext, AccessCounter accessCounter) {
        this.tenantContext = tenantContext;
        this.accessCounter = accessCounter;
    }

    @Override
    public void afterProcessing(Message<T> message, Throwable t) {
        accessCounter.increment(tenantContext.getId());
        log.info("{} has been accessed {} times", tenantContext.getId(), accessCounter.get(tenantContext.getId()));
    }
}
