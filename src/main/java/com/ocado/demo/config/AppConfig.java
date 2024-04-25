package com.ocado.demo.config;

import com.ocado.demo.sqs.receiving.CustomMessageHandlerMethodFactory;
import com.ocado.demo.sqs.receiving.TenantIdMessageInterceptor;
import com.ocado.demo.tenant.TenantContext;
import io.awspring.cloud.sqs.config.SqsListenerConfigurer;
import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    @Profile("step-2")
    MessageInterceptor<Object> tenantIdSqsMessageInterceptor(Environment environment, TenantContext tenantContext) { // T should be Object, otherwise won't be autowired in Spring Cloud AWS autoconfiguration
        return new TenantIdMessageInterceptor<>(environment, tenantContext);
    }

    @Bean
    @Profile("step-2-custom")
    MessageInterceptor<String> tenantIdMessageInterceptor(Environment environment, TenantContext tenantContext) {
        return new TenantIdMessageInterceptor<>(environment, tenantContext);
    }

    @Bean
    @Profile("step-2-custom")
    SqsListenerConfigurer otpSqsListenerConfigurer(List<MessageInterceptor> messageInterceptors) {
        return configurer -> {
            // is not injected as bean due to unwanted effect of afterPropertiesSet method
            var messageHandlerMethodFactory = new CustomMessageHandlerMethodFactory(messageInterceptors);
            configurer.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
        };
    }
}
