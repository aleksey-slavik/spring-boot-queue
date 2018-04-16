package com.globallogic.unit;

import com.globallogic.domain.Message;
import com.globallogic.listener.MessageListener;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Message listener test
 *
 * @author oleksii.slavik
 */
public class MessageListenerTest {

    /**
     * Message text
     */
    private static final String TEST_MESSAGE = "test message";

    /**
     * expected response
     */
    private static final String EXPECTED_RESPONSE = "message in queue: " + TEST_MESSAGE + "\n";

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
        MessageListener listener = new MessageListener();
        Message message = new Message();
        message.setMessage(TEST_MESSAGE);
        listener.receive(message);
        assertEquals(EXPECTED_RESPONSE, out.toString());
    }
}
