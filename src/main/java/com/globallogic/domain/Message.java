package com.globallogic.domain;

/**
 * Domain message object
 *
 * @author oleksii.slavik
 */
public class Message {

    /**
     * message text
     */
    private String message;

    /**
     * @return message text
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message message text
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
