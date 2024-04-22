package com.ocado.demo.sqs.receiving;

import com.ocado.demo.tenant.TenantContext;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;

import java.util.function.Supplier;

@Profile({"step-2", "step-3"})
public class TenantIdMessageInterceptor<T> implements MessageInterceptor<T> {
    private final TenantContext tenantContext;

    public TenantIdMessageInterceptor(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    /**
     * @see io.awspring.cloud.sqs.listener.AsyncComponentAdapters.AbstractThreadingComponentAdapter#execute(Supplier)
     */
    @Override
    public Message<T> intercept(Message<T> message) {
        var tid = message.getHeaders().get("tenantId", String.class);
        tenantContext.set(tid);
        return message;
    }
}
