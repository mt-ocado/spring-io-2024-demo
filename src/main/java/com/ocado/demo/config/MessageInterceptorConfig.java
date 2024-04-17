package com.ocado.demo.config;

import com.ocado.demo.tenant.TenantContext;
import com.ocado.demo.tenant.TenantMessageProcessor;
import com.ocado.demo.tenant.sqs.TenantIdMessageInterceptor;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("message-interceptor")
@Configuration
public class MessageInterceptorConfig {
    @Bean
    MessageInterceptor<Object> tenantIdSqsMessageInterceptor(TenantContext tenantContext, TenantMessageProcessor tenantMessageProcessor) { // T should be Object, otherwise won't be autowired in Spring Cloud AWS autoconfiguration
        return new TenantIdMessageInterceptor<>(tenantContext, tenantMessageProcessor);
    }
}
