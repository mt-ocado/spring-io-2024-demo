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
public class SqsSendingService {
    private static final Logger logger = LoggerFactory.getLogger(SqsSendingService.class);
    private final SqsTemplate sqsTemplate;
    private final List<MessagePostProcessor> messagePostProcessors;

    public SqsSendingService(SqsTemplate sqsTemplate, List<MessagePostProcessor> messagePostProcessors) {
        this.sqsTemplate = sqsTemplate;
        this.messagePostProcessors = messagePostProcessors;
    }

    public <T> void send(String queue, T payload) {
        logger.info("----Sending----");
        Message<?> message = MessageBuilder.withPayload(payload).build();
        for (var messagePostProcessor : messagePostProcessors) {
            message = messagePostProcessor.postProcessMessage(message);
        }
        logger.info("Send the {}", message);
        sqsTemplate.send(queue, message);
    }
}
