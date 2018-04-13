package com.globallogic.integration;

import com.globallogic.domain.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerIntegrationTest {

    private static final String TEST_MESSAGE = "test message";

    @LocalServerPort
    private int port;

    private URL url;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void init() throws Exception {
        this.url = new URL("http://localhost:" + port + "/send");
    }

    @Test
    public void sendMessageTest() throws Exception {
        Message message = new Message();
        message.setMessage(TEST_MESSAGE);
        ResponseEntity<String> response = template.postForEntity(url.toString(), message, String.class);
        assertEquals(204, response.getStatusCodeValue());
    }
}
