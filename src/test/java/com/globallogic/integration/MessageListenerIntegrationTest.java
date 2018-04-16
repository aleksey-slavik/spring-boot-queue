package com.globallogic.integration;

import com.globallogic.domain.Message;
import com.globallogic.service.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Message listener test
 *
 * @author oleksii.slavik
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageListenerIntegrationTest {

    /**
     * test message text
     */
    private static final String TEST_MESSAGE = "test message";

    /**
     * expected response
     */
    private static final String EXPECTED_RESPONSE = "message in queue: " + TEST_MESSAGE + "\n";

    /**
     * message delay in milliseconds
     */
    private static final int MESSAGE_DELAY = 100;

    /**
     * message service
     */
    @Autowired
    private MessageService service;

    /**
     * output stream
     */
    private ByteArrayOutputStream out;

    @Before
    public void before() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    /**
     * Check response of queue listener
     */
    @Test
    public void receiveMessageTest() throws Exception {
        Message message = new Message();
        message.setMessage(TEST_MESSAGE);
        service.sendMessage(message);
        Thread.sleep(MESSAGE_DELAY);
        assertEquals(EXPECTED_RESPONSE, out.toString());
    }
}
