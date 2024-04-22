package com.ocado.demo.tenant.sqs.receiving;

import com.ocado.demo.tenant.TenantContext;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TenantIdSqsListener {
    private final Logger log = LoggerFactory.getLogger(TenantIdSqsListener.class);
    private final TenantContext tenantContext;

    public TenantIdSqsListener(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    @SqsListener("my-queue")
    public void listenMessage(String message) {
        log.info("----Receiving----");
        var tenantId = tenantContext.getId();
        log.info("Hello, {}! Your tenant ID is \"{}\"", message, tenantId);
    }
}