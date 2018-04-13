package com.globallogic.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.buffered.AmazonSQSBufferedAsyncClient;
import com.amazonaws.services.sqs.buffered.QueueBufferConfig;
import org.elasticmq.rest.sqs.SQSRestServer;
import org.elasticmq.rest.sqs.SQSRestServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.*;
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
    @Value("${queue.accessKey}")
    private String accessKey;

    /**
     * SQS secret key
     */
    @Value("${queue.secretKey}")
    private String secretKey;

    /**
     * SQS scheme type
     */
    @Value("${queue.scheme}")
    private String queueScheme;

    /**
     * SQS host name
     */
    @Value("${queue.host}")
    private String queueHost;

    /**
     * SQS queue name
     */
    @Value("${queue.name}")
    private String queueName;

    /**
     * SQS port number
     */
    @Value("${queue.port}")
    private int queuePort;

    /**
     * Maximum amount of time (in milliseconds), that an outgoing call waits for other calls of the same type to bath with
     */
    @Value("${queue.maxBatchOpen}")
    private long queueMaxBatchOpen;

    /**
     * Maximum number of messages, that will be batched together in a single batch request
     */
    @Value("${queue.maxBatchSize}")
    private int queueMaxBatchSize;

    /**
     * Maximum number of active receive batches, that can be process at the same time
     */
    @Value("${queue.maxInflightOutboundBatches}")
    private int queueMaxInflightOutboundBatches;

    /**
     * ElasticMQ uri builder
     *
     * @return ElasticMQ uri
     */
    @Bean
    public UriComponents elasticMQUri() {
        return UriComponentsBuilder.newInstance()
                .scheme(queueScheme)
                .host(queueHost)
                .port(queuePort)
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
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    /**
     * Create SQS client
     *
     * @param credentials credentials for access to queue
     * @param uri         queue uri
     * @return SQS client
     */
    @Lazy
    @DependsOn("sqsRestServer")
    @Bean
    public AmazonSQSAsync amazonSQS(AWSCredentials credentials, UriComponents uri) {
        AmazonSQSAsyncClient client = new AmazonSQSAsyncClient(credentials);
        client.setEndpoint(uri.toUriString());
        client.createQueue(queueName);

        QueueBufferConfig config = new QueueBufferConfig()
                .withMaxBatchOpenMs(queueMaxBatchOpen)
                .withMaxBatchSize(queueMaxBatchSize)
                .withMaxInflightOutboundBatches(queueMaxInflightOutboundBatches);

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
        template.setDefaultDestinationName(queueName);
        return template;
    }
}
