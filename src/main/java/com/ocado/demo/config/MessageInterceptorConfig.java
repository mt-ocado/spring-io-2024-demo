package com.ocado.demo.config;

import com.ocado.demo.tenant.TenantContext;
import com.ocado.demo.tenant.TenantMessageProcessor;
import com.ocado.demo.tenant.observability.AccessCounter;
import com.ocado.demo.tenant.observability.ObservabilityMessageInterceptor;
import com.ocado.demo.tenant.sqs.TenantIdMessageInterceptor;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

@Profile("message-interceptor")
@Configuration
public class MessageInterceptorConfig {
    @Bean
    @Order(1)
    MessageInterceptor<Object> tenantIdSqsMessageInterceptor(TenantContext tenantContext, TenantMessageProcessor tenantMessageProcessor) { // T should be Object, otherwise won't be autowired in Spring Cloud AWS autoconfiguration
        return new TenantIdMessageInterceptor<>(tenantContext, tenantMessageProcessor);
    }

    @Bean
    @Order(2)
    MessageInterceptor<Object> observabilityMessageInterceptor(TenantContext tenantContext, AccessCounter accessCounter) { // T should be Object, otherwise won't be autowired in Spring Cloud AWS autoconfiguration
        return new ObservabilityMessageInterceptor<>(tenantContext, accessCounter);
    }
}
