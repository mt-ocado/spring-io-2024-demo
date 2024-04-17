package com.ocado.demo.config;

import com.ocado.demo.tenant.TenantContext;
import com.ocado.demo.tenant.TenantMessageProcessor;
import com.ocado.demo.tenant.sqs.CustomMessageHandlerMethodFactory;
import com.ocado.demo.tenant.sqs.TenantIdMessageInterceptor;
import io.awspring.cloud.sqs.config.SqsListenerConfigurer;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("custom-message-interceptor")
@Configuration
public class CustomMessageInterceptorConfig {
    @Bean
    MessageInterceptor<String> customTenantIdMessageInterceptor(
            TenantContext tenantContext,
            TenantMessageProcessor tenantMessageProcessor
    ) {
        return new TenantIdMessageInterceptor<>(tenantContext, tenantMessageProcessor);
    }


    @Bean
    SqsListenerConfigurer otpSqsListenerConfigurer(List<MessageInterceptor> messageInterceptors) {
        return registrar -> {
            // is not injected as bean due to unwanted effect of afterPropertiesSet method
            var messageHandlerMethodFactory = new CustomMessageHandlerMethodFactory(messageInterceptors);
            registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
        };
    }
}
