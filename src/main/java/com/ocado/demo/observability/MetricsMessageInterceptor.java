package com.ocado.demo.observability;

import com.ocado.demo.tenant.TenantContext;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@Profile("step-3")
public class MetricsMessageInterceptor implements MessageInterceptor<Object> {
    private final Logger log = LoggerFactory.getLogger(MetricsMessageInterceptor.class);
    private final TenantContext tenantContext;
    private final TenantMetrics tenantMetrics;

    public MetricsMessageInterceptor(TenantContext tenantContext, TenantMetrics tenantMetrics) {
        this.tenantContext = tenantContext;
        this.tenantMetrics = tenantMetrics;
    }

    @Override
    public void afterProcessing(Message<Object> message, Throwable t) {
        tenantMetrics.increment(tenantContext.getId());
        log.info("{} has been accessed {} times", tenantContext.getId(), tenantMetrics.get(tenantContext.getId()));
    }
}
