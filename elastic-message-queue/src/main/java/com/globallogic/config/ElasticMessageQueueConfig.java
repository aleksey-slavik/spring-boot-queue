package com.globallogic.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.buffered.AmazonSQSBufferedAsyncClient;
import com.amazonaws.services.sqs.buffered.QueueBufferConfig;
import org.elasticmq.rest.sqs.SQSRestServer;
import org.elasticmq.rest.sqs.SQSRestServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class ElasticMessageQueueConfig {

    private static final String ACCESS_KEY = "";

    private static final String SECRET_KEY = "";

    private static final String QUEUE_SCHEME = "http";

    private static final String QUEUE_HOST = "localhost";

    private static final String QUEUE_NAME = "spring-boot-queue";

    private static final int QUEUE_PORT = 9324;

    private static final long QUEUE_MAX_BATCH_OPEN = 200;

    private static final int QUEUE_MAX_BATCH_SIZE = 10;

    private static final int QUEUE_MAX_INFLIGHT_OUTBOUND_BATCHES = 5;

    @Bean
    public UriComponents elasticMQLocalSQSUri() {
        return UriComponentsBuilder.newInstance()
                .scheme(QUEUE_SCHEME)
                .host(QUEUE_HOST)
                .port(QUEUE_PORT)
                .build()
                .encode();
    }

    @Bean
    public SQSRestServer sqsRestServer(UriComponents uri) {
        return SQSRestServerBuilder
                .withPort(uri.getPort())
                .withInterface(uri.getHost())
                .start();
    }

    @Bean
    public AWSCredentials awsCredentials() {
        return new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
    }

    @Bean
    public AmazonSQSAsync amazonSQS(AWSCredentials credentials, UriComponents uri) {
        AmazonSQSAsyncClient client = new AmazonSQSAsyncClient(credentials);
        client.setEndpoint(uri.toUriString());
        client.createQueue(QUEUE_NAME);

        QueueBufferConfig config = new QueueBufferConfig()
                .withMaxBatchOpenMs(QUEUE_MAX_BATCH_OPEN)
                .withMaxBatchSize(QUEUE_MAX_BATCH_SIZE)
                .withMaxInflightOutboundBatches(QUEUE_MAX_INFLIGHT_OUTBOUND_BATCHES);

        return new AmazonSQSBufferedAsyncClient(client, config);
    }
}
