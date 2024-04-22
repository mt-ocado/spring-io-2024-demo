package com.ocado.demo.config;

import com.ocado.demo.tenant.TenantContext;
import com.ocado.demo.tenant.TenantMessageProcessor;
import com.ocado.demo.tenant.observability.AccessCounter;
import com.ocado.demo.tenant.observability.ObservabilityMessageInterceptor;
import com.ocado.demo.tenant.sqs.receiving.CustomMessageHandlerMethodFactory;
import com.ocado.demo.tenant.sqs.receiving.TenantIdMessageInterceptor;
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
    MessageInterceptor<String> customTenantIdMessageInterceptor(TenantContext tenantContext, TenantMessageProcessor tenantMessageProcessor) {
        return new TenantIdMessageInterceptor<>(tenantContext, tenantMessageProcessor);
    }

    @Bean
    @Order(2)
    MessageInterceptor<String> observabilityMessageInterceptor(TenantContext tenantContext, AccessCounter accessCounter) { // T should be Object, otherwise won't be autowired in Spring Cloud AWS autoconfiguration
        return new ObservabilityMessageInterceptor<>(tenantContext, accessCounter);
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
