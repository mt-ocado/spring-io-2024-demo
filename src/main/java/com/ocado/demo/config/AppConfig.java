package com.ocado.demo.config;

import com.ocado.demo.sqs.SqsAsyncClientCustomizer;
import com.ocado.demo.tenant.TenantContext;
import com.ocado.demo.tenant.TenantMessageProcessor;
import com.ocado.demo.tenant.sqs.TenantIdSqsListener;
import io.awspring.cloud.core.region.StaticRegionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.providers.AwsRegionProvider;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    TenantContext tenantContext() {
        return new TenantContext();
    }

    @Bean
    TenantIdSqsListener tenantIdSqsListener(TenantContext tenantContext) {
        return new TenantIdSqsListener(tenantContext);
    }

    @Bean
    TenantMessageProcessor tenantMessageProcessor() {
        return new TenantMessageProcessor();
    }

    @Bean
    AwsCredentialsProvider awsCredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create("foo", "foo"));
    }

    @Bean
    AwsRegionProvider awsRegionProvider() {
        return new StaticRegionProvider(Region.US_EAST_1.id());
    }

    @Bean
    SqsAsyncClientCustomizer localstackSqsAsyncClientCustomizer(
            AwsCredentialsProvider awsCredentialsProvider,
            AwsRegionProvider awsRegionProvider
    ) {
        var endpoint = URI.create("http://localhost:4566");
        return builder -> builder
                .endpointOverride(endpoint)
                .credentialsProvider(awsCredentialsProvider)
                .region(awsRegionProvider.getRegion());
    }

    @Bean
    SqsAsyncClient sqsAsyncClient(List<SqsAsyncClientCustomizer> sqsAsyncClientCustomizers) {
        var clientBuilder = SqsAsyncClient.builder();
        for (var sqsAsyncClientCustomizer : sqsAsyncClientCustomizers) {
            sqsAsyncClientCustomizer.customize(clientBuilder);
        }
        return clientBuilder.build();
    }
}
