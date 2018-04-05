package com.globallogic.service;

import com.globallogic.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService {

    private QueueMessagingTemplate messagingTemplate;

    @Autowired
    public SendMessageService(QueueMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessage(Message message) {
        messagingTemplate.convertAndSend(message);
    }
}
