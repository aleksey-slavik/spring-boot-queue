package com.globallogic.service;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService {

    private static final String QUEUE_ENDPOINT = "http://localhost:9324/queue/spring-boot-queue";

    public void sendMessage(String message) {
        AmazonSQSClient client = new AmazonSQSClient();

        SendMessageRequest messageRequest = new SendMessageRequest()
                .withQueueUrl(QUEUE_ENDPOINT)
                .withMessageBody(message);

        client.sendMessage(messageRequest);
    }
}
