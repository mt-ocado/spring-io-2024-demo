package com.ocado.demo.sqs.receiving;

import com.ocado.demo.tenant.TenantContext;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"step-2", "step-3", "step-4"})
public class TenantIdSqsListener {
    private static final Logger logger = LoggerFactory.getLogger(TenantIdSqsListener.class);
    private final TenantContext tenantContext;

    public TenantIdSqsListener(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    @SqsListener("${sqs.queue}")
    public void listenMessage(String payload) {
        logger.info("----Receiving----");
        var tenantId = tenantContext.get();
        logger.info("Hello, {}! Your tenant ID is \"{}\"", payload, tenantId);
    }
}
