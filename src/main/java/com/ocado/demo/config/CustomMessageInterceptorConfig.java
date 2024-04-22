package com.ocado.demo.config;

import com.ocado.demo.observability.MetricsMessageInterceptor;
import com.ocado.demo.observability.TenantMetrics;
import com.ocado.demo.sqs.receiving.CustomMessageHandlerMethodFactory;
import com.ocado.demo.sqs.receiving.TenantIdMessageInterceptor;
import com.ocado.demo.tenant.TenantContext;
import io.awspring.cloud.sqs.config.SqsListenerConfigurer;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
@Profile("step-4")
public class CustomMessageInterceptorConfig {
    @Bean
    @Order(1)
    MessageInterceptor<String> tenantIdMessageInterceptor(TenantContext tenantContext) {
        return new TenantIdMessageInterceptor<>(tenantContext);
    }

    @Bean
    @Order(2)
    MessageInterceptor<String> metricsMessageInterceptor(TenantContext tenantContext, TenantMetrics tenantMetrics) { // T should be Object, otherwise won't be autowired in Spring Cloud AWS autoconfiguration
        return new MetricsMessageInterceptor<>(tenantContext, tenantMetrics);
    }

    @Bean
    SqsListenerConfigurer otpSqsListenerConfigurer(List<MessageInterceptor> messageInterceptors) {
        return configurer -> {
            // is not injected as bean due to unwanted effect of afterPropertiesSet method
            var messageHandlerMethodFactory = new CustomMessageHandlerMethodFactory(messageInterceptors);
            configurer.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
        };
    }
}
