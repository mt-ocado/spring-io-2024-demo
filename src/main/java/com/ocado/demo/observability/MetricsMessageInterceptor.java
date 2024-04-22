package com.ocado.demo.observability;

import com.ocado.demo.tenant.TenantContext;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;

@Profile("step-3")
public class MetricsMessageInterceptor<T> implements MessageInterceptor<T> {
    private static final Logger logger = LoggerFactory.getLogger(MetricsMessageInterceptor.class);
    private final TenantContext tenantContext;
    private final TenantMetrics tenantMetrics;

    public MetricsMessageInterceptor(TenantContext tenantContext, TenantMetrics tenantMetrics) {
        this.tenantContext = tenantContext;
        this.tenantMetrics = tenantMetrics;
    }

    @Override
    public void afterProcessing(Message<T> message, Throwable t) {
        tenantMetrics.increment();
        logger.info("{} has been accessed {} times", tenantContext.get(), tenantMetrics.get());
    }
}
