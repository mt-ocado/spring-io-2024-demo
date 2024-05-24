package com.ocado.demo.sqs.receiving;

import com.ocado.demo.tenant.TenantContext;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"step-2", "step-2-custom"})
public class TenantIdSqsListener {
    //<editor-fold desc="// private fields">
    private static final Logger logger = LoggerFactory.getLogger(TenantIdSqsListener.class);
    private final TenantContext tenantContext;
    //</editor-fold>

    //<editor-fold desc="// default constructor">
    public TenantIdSqsListener(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }
    //</editor-fold>

    @SqsListener("${sqs.queue}")
    public void listenMessage(String messageBody) {
        var tenantId = tenantContext.get();
        logger.info("c. Handle message \"{}\" from \"{}\"", messageBody, tenantId);
    }
}
