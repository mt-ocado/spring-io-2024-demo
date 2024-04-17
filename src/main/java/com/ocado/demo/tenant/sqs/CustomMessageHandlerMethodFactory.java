package com.ocado.demo.tenant.sqs;

import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.lang.reflect.Method;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CustomMessageHandlerMethodFactory extends DefaultMessageHandlerMethodFactory {
    private final List<MessageInterceptor> messageInterceptors;

    @Override
    public InvocableHandlerMethod createInvocableHandlerMethod(Object bean, Method method) {
        var handlerMethod = super.createInvocableHandlerMethod(bean, method);
        return new CustomInvocableHandlerMethod(bean, method, handlerMethod, messageInterceptors);
    }

    static class CustomInvocableHandlerMethod extends InvocableHandlerMethod {
        private final InvocableHandlerMethod handlerMethod;
        private final List<MessageInterceptor> messageInterceptors;

        CustomInvocableHandlerMethod(Object bean, Method method, InvocableHandlerMethod handlerMethod, List<MessageInterceptor> messageInterceptors) {
            super(bean, method);
            this.handlerMethod = handlerMethod;
            this.messageInterceptors = messageInterceptors;
        }

        @Override
        public Object invoke(Message<?> message, Object... providedArgs) throws Exception {
            log.info("----Custom Message Interceptor----");
            messageInterceptors.forEach(interceptor -> interceptor.intercept(message));
            var result = handlerMethod.invoke(message, providedArgs);
            messageInterceptors.forEach(interceptor -> interceptor.afterProcessing(message, null));
            return result;
        }
    }
}