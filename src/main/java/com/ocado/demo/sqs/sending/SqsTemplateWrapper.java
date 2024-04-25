package com.ocado.demo.sqs.sending;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SqsTemplateWrapper {

    //<editor-fold desc="// private fields">
    private static final Logger logger = LoggerFactory.getLogger(SqsTemplateWrapper.class);
    private final SqsTemplate sqsTemplate;
    private final List<MessagePostProcessor> messagePostProcessors;
    //</editor-fold>

    //<editor-fold desc="// default constructor">
    public SqsTemplateWrapper(SqsTemplate sqsTemplate, List<MessagePostProcessor> messagePostProcessors) {
        this.sqsTemplate = sqsTemplate;
        this.messagePostProcessors = messagePostProcessors;
    }
    //</editor-fold>

    public <T> void send(String queue, T payload) {
        Message<?> message = MessageBuilder.withPayload(payload).build();
        for (var messagePostProcessor : messagePostProcessors) {
            message = messagePostProcessor.postProcessMessage(message);
        }
        sqsTemplate.send(queue, message);

        //<editor-fold desc="// logs">
        logger.info("c. Send the {}", message);
        //</editor-fold>
    }
}
