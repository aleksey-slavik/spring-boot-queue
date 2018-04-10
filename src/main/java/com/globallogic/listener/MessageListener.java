package com.globallogic.listener;

import com.globallogic.domain.Message;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

/**
 * Message listener
 *
 * @author oleksii.slavik
 */
@Component
@EnableSqs
public class MessageListener {

    /**
     * Listener for queue with name 'spring-boot-queue'
     *
     * @param message received message
     */
    @SqsListener("spring-boot-queue")
    public void receive(Message message) {
        System.out.println("message in queue: " + message.getMessage());
    }
}
