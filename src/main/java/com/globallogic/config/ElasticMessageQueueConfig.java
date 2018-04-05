package com.globallogic.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.buffered.AmazonSQSBufferedAsyncClient;
import com.amazonaws.services.sqs.buffered.QueueBufferConfig;
import org.elasticmq.rest.sqs.SQSRestServer;
import org.elasticmq.rest.sqs.SQSRestServerBuilder;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Application configuration
 *
 * @author oleksii.slavik
 */
@Configuration
public class ElasticMessageQueueConfig {

    /**
     * SQS access key
     */
    private static final String ACCESS_KEY = "";

    /**
     * SQS secret key
     */
    private static final String SECRET_KEY = "";

    /**
     * SQS scheme type
     */
    private static final String QUEUE_SCHEME = "http";

    /**
     * SQS host name
     */
    private static final String QUEUE_HOST = "localhost";

    /**
     * SQS queue name
     */
    private static final String QUEUE_NAME = "spring-boot-queue";

    /**
     * SQS port number
     */
    private static final int QUEUE_PORT = 9324;

    /**
     * Maximum amount of time (in milliseconds), that an outgoing call waits for other calls of the same type to bath with
     */
    private static final long QUEUE_MAX_BATCH_OPEN = 200;

    /**
     * Maximum number of messages, that will be batched together in a single batch request
     */
    private static final int QUEUE_MAX_BATCH_SIZE = 10;

    /**
     * Maximum number of active receive batches, that can be process at the same time
     */
    private static final int QUEUE_MAX_INFLIGHT_OUTBOUND_BATCHES = 5;

    /**
     * ElasticMQ uri builder
     *
     * @return ElasticMQ uri
     */
    @Bean
    public UriComponents elasticMQUri() {
        return UriComponentsBuilder.newInstance()
                .scheme(QUEUE_SCHEME)
                .host(QUEUE_HOST)
                .port(QUEUE_PORT)
                .build()
                .encode();
    }

    /**
     * Create ElasticMQ rest server object
     *
     * @param uri ElasticMQ uri
     * @return ElasticMQ rest server
     */
    @Bean
    public SQSRestServer sqsRestServer(UriComponents uri) {
        return SQSRestServerBuilder
                .withPort(uri.getPort())
                .withInterface(uri.getHost())
                .start();
    }

    /**
     * Create credentials for access to queue
     *
     * @return credentials
     */
    @Bean
    public AWSCredentials awsCredentials() {
        return new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
    }

    /**
     * Create SQS client
     *
     * @param credentials credentials for access to queue
     * @param uri         queue uri
     * @return SQS client
     */
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

    /**
     * Messaging template for send messages to queue
     *
     * @param sqs SQS client
     * @return messaging template
     */
    @Bean
    public QueueMessagingTemplate messagingTemplate(AmazonSQSAsync sqs) {
        QueueMessagingTemplate template = new QueueMessagingTemplate(sqs);
        template.setDefaultDestinationName(QUEUE_NAME);
        return template;
    }
}
