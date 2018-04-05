package com.globallogic.rest;

import com.globallogic.domain.Message;
import com.globallogic.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private SendMessageService service;

    @Autowired
    public MessageController(SendMessageService service) {
        this.service = service;
    }

    @RequestMapping(
            value = "/send",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> sendMessage(@RequestBody Message message){
        service.sendMessage(message);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
