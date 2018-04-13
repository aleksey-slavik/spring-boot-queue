package com.globallogic.unit;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.domain.Message;
import com.globallogic.service.MessageService;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Message service test
 *
 * @author oleksii.slavik
 */
public class MessageServiceTest {

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
     * SQS endpoint
     */
    private static final String SQS_ENDPOINT = "http://" + SQS_HOST + ":" + SQS_PORT;

    /**
     * Message text
     */
    private static final String TEST_MESSAGE = "test message";

    /**
     * Check send message
     */
    @Test
    public void sendMessageTest() throws Exception {
        AmazonSQSAsync amazonSQS = createAmazonSqs();
        QueueMessagingTemplate messagingTemplate = new QueueMessagingTemplate(amazonSQS);
        messagingTemplate.setDefaultDestinationName(QUEUE_NAME);
        MessageService messageService = new MessageService(messagingTemplate);

        Message message = new Message();
        message.setMessage(TEST_MESSAGE);
        messageService.sendMessage(message);

        ArgumentCaptor<SendMessageRequest> captor = ArgumentCaptor.forClass(SendMessageRequest.class);
        verify(amazonSQS).sendMessage(captor.capture());
        assertEquals(SQS_ENDPOINT, captor.getValue().getQueueUrl());

        ObjectMapper mapper = new ObjectMapper();
        Message actual = mapper.readValue(captor.getValue().getMessageBody(), Message.class);
        assertEquals(TEST_MESSAGE, actual.getMessage());
    }

    /**
     * Create mocked amazon sqs client
     *
     * @return sqs client
     */
    private AmazonSQSAsync createAmazonSqs() {
        AmazonSQSAsync amazonSqs = mock(AmazonSQSAsync.class);
        GetQueueUrlResult queueUrl = new GetQueueUrlResult();
        queueUrl.setQueueUrl(SQS_ENDPOINT);
        when(amazonSqs.getQueueUrl(any(GetQueueUrlRequest.class))).thenReturn(queueUrl);
        return amazonSqs;
    }
}
