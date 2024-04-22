package com.ocado.demo.tenant.sqs.receiving;

import com.ocado.demo.tenant.TenantContext;
import com.ocado.demo.tenant.TenantMessageProcessor;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import java.util.function.Supplier;

public class TenantIdMessageInterceptor<T> implements MessageInterceptor<T> {
    private final Logger log = LoggerFactory.getLogger(TenantIdMessageInterceptor.class);
    private final TenantContext tenantContext;
    private final TenantMessageProcessor tenantMessageProcessor;

    public TenantIdMessageInterceptor(TenantContext tenantContext, TenantMessageProcessor tenantMessageProcessor) {
        this.tenantContext = tenantContext;
        this.tenantMessageProcessor = tenantMessageProcessor;
    }

    /**
     * @see io.awspring.cloud.sqs.listener.AsyncComponentAdapters.AbstractThreadingComponentAdapter#execute(Supplier)
     */
    @Override
    public Message<T> intercept(Message<T> message) {
        var tid = this.tenantMessageProcessor.extract(message);
        tenantContext.setId(tid);
        return message;
    }
}
