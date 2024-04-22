package com.ocado.demo.config;

import com.ocado.demo.observability.MetricsMessageInterceptor;
import com.ocado.demo.observability.TenantMetrics;
import com.ocado.demo.sqs.receiving.TenantIdMessageInterceptor;
import com.ocado.demo.tenant.TenantContext;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

@Configuration
public class MessageInterceptorConfig {
    @Bean
    @Order(1)
    @Profile({"step-2", "step-3"})
    MessageInterceptor<Object> tenantIdSqsMessageInterceptor(TenantContext tenantContext) { // T should be Object, otherwise won't be autowired in Spring Cloud AWS autoconfiguration
        return new TenantIdMessageInterceptor<>(tenantContext);
    }

    @Bean
    @Order(2)
    @Profile("step-3")
    MessageInterceptor<Object> observabilityMessageInterceptor(TenantContext tenantContext, TenantMetrics tenantMetrics) { // T should be Object, otherwise won't be autowired in Spring Cloud AWS autoconfiguration
        return new MetricsMessageInterceptor<>(tenantContext, tenantMetrics);
    }
}
