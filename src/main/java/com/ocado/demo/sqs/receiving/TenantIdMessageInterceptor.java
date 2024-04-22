package com.ocado.demo.sqs.receiving;

import com.ocado.demo.tenant.TenantContext;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


@Component
@Order(1)
@Profile({"step-2", "step-3"})
public class TenantIdMessageInterceptor implements MessageInterceptor<Object> {
    private final TenantContext tenantContext;

    public TenantIdMessageInterceptor(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    @Override
    public Message<Object> intercept(Message<Object> message) {
        var tid = message.getHeaders().get("tenantId", String.class);
        tenantContext.setId(tid);
        return message;
    }
}
