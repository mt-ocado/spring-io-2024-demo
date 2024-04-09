package com.ocado.demo.sqs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.lang.reflect.Method;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class OtpMessageHandlerMethodFactory extends DefaultMessageHandlerMethodFactory {
    private final List<SqsListenerEffect> sqsListenerEffects;

    @Override
    public InvocableHandlerMethod createInvocableHandlerMethod(Object bean, Method method) {
        var handlerMethod = super.createInvocableHandlerMethod(bean, method);
        return new InvocableHandlerMethodWithEffects(bean, method, handlerMethod, sqsListenerEffects);
    }

    static class InvocableHandlerMethodWithEffects extends InvocableHandlerMethod {
        private final InvocableHandlerMethod handlerMethod;
        private final List<SqsListenerEffect> sqsListenerEffects;

        InvocableHandlerMethodWithEffects(Object bean, Method method, InvocableHandlerMethod handlerMethod, List<SqsListenerEffect> sqsListenerEffects) {
            super(bean, method);
            this.handlerMethod = handlerMethod;
            this.sqsListenerEffects = sqsListenerEffects;
        }

        @Override
        public Object invoke(Message<?> message, Object... providedArgs) throws Exception {
            sqsListenerEffects.forEach(effect -> effect.before(message));
            var result = handlerMethod.invoke(message, providedArgs);
            sqsListenerEffects.forEach(effect -> effect.after(message));
            return result;
        }
    }
}
