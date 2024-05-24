package com.ocado.demo.sqs.receiving;

import io.awspring.cloud.sqs.listener.interceptor.MessageInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;

public class CustomMessageHandlerMethodFactory extends DefaultMessageHandlerMethodFactory {
    //<editor-fold desc="// private fields">
    private final List<MessageInterceptor> messageInterceptors;
    //</editor-fold>

    //<editor-fold desc="// default constructor">
    public CustomMessageHandlerMethodFactory(List<MessageInterceptor> messageInterceptors) {
        this.messageInterceptors = messageInterceptors;
    }
    //</editor-fold>

    @Override
    public InvocableHandlerMethod createInvocableHandlerMethod(Object bean, Method method) {
        var handlerMethod = super.createInvocableHandlerMethod(bean, method);
        return new InvocableHandlerMethodWrapper(bean, method, handlerMethod, messageInterceptors);
    }

    static class InvocableHandlerMethodWrapper extends InvocableHandlerMethod {
        //<editor-fold desc="// private fields">
        private final Logger log = Logger.getLogger(InvocableHandlerMethodWrapper.class.getName());
        private final InvocableHandlerMethod handlerMethod;
        private final List<MessageInterceptor> interceptors;
        //</editor-fold>

        //<editor-fold desc="// default constructor">
        InvocableHandlerMethodWrapper(Object bean, Method method, InvocableHandlerMethod handlerMethod, List<MessageInterceptor> interceptors) {
            super(bean, method);
            this.handlerMethod = handlerMethod;
            this.interceptors = interceptors;
        }
        //</editor-fold>

        @Override
        public Object invoke(Message<?> message, Object... providedArgs) throws Exception {
            //<editor-fold desc="// logs">
            log.info("[Custom Message Interceptor is used]");
            //</editor-fold>

            interceptors.forEach(interceptor -> interceptor.intercept(message));
            var result = handlerMethod.invoke(message, providedArgs);
            interceptors.forEach(interceptor -> interceptor.afterProcessing(message, null));
            return result;
        }
    }
}
