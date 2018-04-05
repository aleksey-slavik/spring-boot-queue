package com.globallogic.service;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.globallogic.domain.Message;
import org.elasticmq.rest.sqs.SQSRestServer;
import org.elasticmq.rest.sqs.SQSRestServerBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;

import static org.junit.Assert.assertEquals;

/**
 * Message service test
 *
 * @author oleksii.slavik
 */
public class MessageServiceTest {

    /**
     * SQS access key
     */
    private static final String SQS_ACCESS_KEY = "";

    /**
     * SQS secret key
     */
    private static final String SQS_SECRET_KEY = "";

    /**
     * SQS queue name
     */
    private static final String QUEUE_NAME = "spring-boot-queue";

    /**
     * SQS port number
     */
    private static final int SQS_PORT = 9324;

    /**
     * SQS host name
     */
    private static final String SQS_HOST = "localhost";

    /**
     * Message text
     */
    private static final String TEST_MESSAGE = "test message";

    /**
     * ElasticMQ rest server object
     */
    private SQSRestServer sqsRestServer;

    /**
     * Message service
     */
    private MessageService messageService;

    /**
     * Messaging template
     */
    private QueueMessagingTemplate messagingTemplate;

    @Before
    public void before() {
        sqsRestServer = SQSRestServerBuilder
                .withPort(SQS_PORT)
                .withInterface(SQS_HOST)
                .start();

        AmazonSQSAsyncClient client = new AmazonSQSAsyncClient(new BasicAWSCredentials(SQS_ACCESS_KEY, SQS_SECRET_KEY));
        client.setEndpoint("http://" + SQS_HOST + ":" + SQS_PORT);
        client.createQueue(QUEUE_NAME);

        messagingTemplate = new QueueMessagingTemplate(client);
        messagingTemplate.setDefaultDestinationName(QUEUE_NAME);

        messageService = new MessageService(messagingTemplate);
    }

    @After
    public void after() {
        if (sqsRestServer != null) {
            sqsRestServer.stopAndWait();
        }
    }

    /**
     * Check send message
     */
    @Test
    public void sendMessageTest() throws Exception {
        Message message = new Message();
        message.setMessage(TEST_MESSAGE);
        messageService.sendMessage(message);
        Message response = messagingTemplate.receiveAndConvert(QUEUE_NAME, Message.class);
        assertEquals(TEST_MESSAGE, response.getMessage());
    }
}
