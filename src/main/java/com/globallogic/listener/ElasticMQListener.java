package com.globallogic.listener;

import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;

@Component
public class ElasticMQListener implements MessageListener {

    @Override
    public void onMessage(Message message) {

    }
}
