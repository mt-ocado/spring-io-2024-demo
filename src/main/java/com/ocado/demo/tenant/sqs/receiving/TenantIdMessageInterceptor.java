package com.ocado.demo.tenant.sqs.receiving;

import com.ocado.demo.tenant.TenantContext;
import com.ocado.demo.tenant.TenantMessageProcessor;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import org.springframework.messaging.Message;

import java.util.function.Supplier;

public class TenantIdMessageInterceptor<T> implements MessageInterceptor<T> {
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
        var tid = this.tenantMessageProcessor.extractFromAttributes(message);
        tenantContext.setId(tid);
        return message;
    }
}
