package com.globallogic.service;

import com.globallogic.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Message service for send messages to queue
 *
 * @author oleksii.slavik
 */
@Service
public class MessageService {

    /**
     * messaging template
     */
    private QueueMessagingTemplate messagingTemplate;

    @Autowired
    public MessageService(QueueMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Send message to queue
     *
     * @param message message
     */
    public void sendMessage(Message message) {
        messagingTemplate.convertAndSend(message);
    }
}
