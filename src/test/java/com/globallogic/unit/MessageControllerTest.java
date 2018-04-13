package com.globallogic.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.domain.Message;
import com.globallogic.rest.MessageController;
import com.globallogic.service.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MessageControllerTest {

    private static final String TEST_MESSAGE = "test message";

    private MockMvc mvc;

    @InjectMocks
    private MessageController controller;

    @Mock
    private MessageService service;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void sendMessageTest() throws Exception {
        Message message = new Message();
        message.setMessage(TEST_MESSAGE);
        Mockito.doNothing().when(service).sendMessage(message);

        mvc.perform(post("/api/send")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsBytes(message)))
                .andExpect(status().isNoContent());
    }
}
