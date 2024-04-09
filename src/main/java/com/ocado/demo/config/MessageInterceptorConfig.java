package com.ocado.demo.config;

import com.ocado.demo.sqs.OtpMessageHandlerMethodFactory;
import com.ocado.demo.tenant.TenantContext;
import com.ocado.demo.tenant.TenantMessageProcessor;
import com.ocado.demo.tenant.sqs.TenantIdSqsMessageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("message-interceptor")
@Configuration
public class MessageInterceptorConfig {
    @Bean
    TenantIdSqsMessageInterceptor tenantIdSqsMessageInterceptor(TenantContext tenantContext, TenantMessageProcessor tenantMessageProcessor) {
        return new TenantIdSqsMessageInterceptor(tenantContext, tenantMessageProcessor);
    }
}
