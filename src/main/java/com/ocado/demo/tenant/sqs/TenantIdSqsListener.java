package com.ocado.demo.tenant.sqs;

import com.ocado.demo.tenant.TenantContext;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TenantIdSqsListener {
    private Logger log = LoggerFactory.getLogger(TenantIdSqsListener.class);
    private final TenantContext tenantContext;

    public TenantIdSqsListener(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    @SqsListener("my-queue")
    public void listenMessage(String message) {
        var tenantId = tenantContext.getId();
        log.info("Hello, {}! Your tenant ID is \"{}\"", message, tenantId);
    }
}
