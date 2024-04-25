package com.ocado.demo.sqs.receiving;

import com.ocado.demo.tenant.TenantContext;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.messaging.Message;

import java.util.Arrays;
import java.util.function.Supplier;

public class TenantIdMessageInterceptor<T> implements MessageInterceptor<T> {
    //<editor-fold desc="// private fields">
    private static final Logger logger = LoggerFactory.getLogger(TenantIdMessageInterceptor.class);
    private final Environment environment;
    private final TenantContext tenantContext;
    //</editor-fold>

    //<editor-fold desc="// default constructor">
    public TenantIdMessageInterceptor(Environment environment, TenantContext tenantContext) {
        this.environment = environment;
        this.tenantContext = tenantContext;
    }
    //</editor-fold>

    /**
     * @see io.awspring.cloud.sqs.listener.AsyncComponentAdapters.AbstractThreadingComponentAdapter#execute(Supplier)
     */
    @Override
    public Message<T> intercept(Message<T> message) {
        var tid = message.getHeaders().get("tenantId", String.class);
        tenantContext.set(tid);

        //<editor-fold desc="// logs">
        var isCustom = (Arrays.asList(environment.getActiveProfiles()).contains("step-2-custom"));
        logger.info("----Step 2. Receiving{}----", isCustom ? " (Custom Message Interceptor)" : "");
        logger.info("a. Receive message from SQS queue and retrieve tenant ID");
        logger.info("b. Set tenant ID \"{}\" in tenant context", tid);
        return message;
        //</editor-fold>
    }
}
