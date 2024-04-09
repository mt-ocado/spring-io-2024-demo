package com.ocado.demo.config;

import com.ocado.demo.sqs.OtpMessageHandlerMethodFactory;
import com.ocado.demo.sqs.SqsListenerEffect;
import com.ocado.demo.tenant.TenantContext;
import com.ocado.demo.tenant.TenantMessageProcessor;
import com.ocado.demo.tenant.sqs.TenantIdSqsListenerEffect;
import io.awspring.cloud.sqs.config.SqsListenerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("listener-effect")
@Configuration
public class ListenerEffectConfig {
    @Bean
    TenantIdSqsListenerEffect tenantIdSqsListenerEffect(
            TenantContext tenantContext,
            TenantMessageProcessor tenantMessageProcessor
    ) {
        return new TenantIdSqsListenerEffect(tenantContext, tenantMessageProcessor);
    }


    @Bean
    SqsListenerConfigurer otpSqsListenerConfigurer(List<SqsListenerEffect> sqsListenerEffects) {
        return registrar -> {
            // is not injected as bean due to unwanted effect of afterPropertiesSet method
            var messageHandlerMethodFactory = new OtpMessageHandlerMethodFactory(sqsListenerEffects);
            registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
        };
    }
}
