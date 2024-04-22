package com.ocado.demo.tenant.sqs.sending;

import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomSqsTemplate {
    private static final Logger log = LoggerFactory.getLogger(CustomSqsTemplate.class);
    private final SqsTemplate sqsTemplate;
    private final List<MessagePostProcessor> messagePostProcessors;

    public CustomSqsTemplate(SqsTemplate sqsTemplate, List<MessagePostProcessor> messagePostProcessors) {
        this.sqsTemplate = sqsTemplate;
        this.messagePostProcessors = messagePostProcessors;
    }

    public <T> SendResult<T> send(String queue, T payload) {
        log.info("----Sending----");
        var message = MessageBuilder.withPayload(payload).build();
        for (var messagePostProcessor : messagePostProcessors) {
            message = (Message<T>) messagePostProcessor.postProcessMessage(message);
        }
        log.info("Send the {}", message);
        return sqsTemplate.send(queue, message);
    }

}
