package com.ocado.demo.tenant.sqs.receiving;

import com.ocado.demo.tenant.TenantContext;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"step-2", "step-3", "step-4"})
@Component
public class TenantIdSqsListener {
    private final Logger log = LoggerFactory.getLogger(TenantIdSqsListener.class);
    private final TenantContext tenantContext;

    public TenantIdSqsListener(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    @SqsListener("${sqs.queue}")
    public void listenMessage(String message) {
        log.info("----Receiving----");
        var tenantId = tenantContext.getId();
        log.info("Hello, {}! Your tenant ID is \"{}\"", message, tenantId);
    }
}
