package com.globallogic.rest;

import com.globallogic.domain.Message;
import com.globallogic.service.MessageService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for operation with messages
 */
@RestController
@RequestMapping(value = "/api")
@Api(value = "/api", description = "Operations with messages", consumes="application/json")
public class MessageController {

    /**
     * message service
     */
    private MessageService service;

    @Autowired
    public MessageController(MessageService service) {
        this.service = service;
    }

    /**
     * Send message
     *
     * @param message message
     */
    @ApiOperation(value = "Send message to default queue")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Message sent to default queue")})
    @RequestMapping(
            value = "/send",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> sendMessage(
            @ApiParam(value = "message that need to be send", required = true) @RequestBody Message message) {
        service.sendMessage(message);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
