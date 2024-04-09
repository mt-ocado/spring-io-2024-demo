package com.ocado.demo.sqs;

import software.amazon.awssdk.services.sqs.SqsAsyncClientBuilder;

@FunctionalInterface
public interface SqsAsyncClientCustomizer {
    void customize(SqsAsyncClientBuilder sqsAsyncClientBuilder);
}
