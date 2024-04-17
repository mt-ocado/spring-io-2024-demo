package com.ocado.demo.tenant.sqs;

import com.ocado.demo.tenant.TenantContext;
import com.ocado.demo.tenant.TenantMessageProcessor;
import io.awspring.cloud.sqs.listener.MessageProcessingContext;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
public class TenantIdMessageInterceptor<T> implements MessageInterceptor<T> {
    private final TenantContext tenantContext;
    private final TenantMessageProcessor tenantMessageProcessor;


    /**
     * @see io.awspring.cloud.sqs.listener.pipeline.AbstractBeforeProcessingInterceptorExecutionStage#process(Message, MessageProcessingContext)
     * @see io.awspring.cloud.sqs.listener.AsyncComponentAdapters.AbstractThreadingComponentAdapter#execute(Supplier)
     */
    @Override
    public Message<T> intercept(Message<T> message) {
        log.info("Intercept");
        if (tenantMessageProcessor.isValid(message)) {
            var tid = this.tenantMessageProcessor.extract(message);
            tenantContext.setId(tid);
        } else {
            log.error("Invalid message");
        }
        return message;
    }

    @Override
    public void afterProcessing(Message<T> message, Throwable t) {
        log.info("After processing");
        tenantContext.clearId();
    }
}
