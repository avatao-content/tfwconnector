/* 
 * Copyright (C) 2018 Avatao.com Innovative Learning Kft.
 * All Rights Reserved. See LICENSE file for details.
 */

package com.avatao.tfw.tfwconnector;

import java.util.List;

import com.avatao.tfw.tfwconnector.TFWServerConnector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 *  Provides a mechanism to send messages to our frontend messaging component which
 *  displays messages with the key "message".
 */
public class MessageSender {
    private TFWServerConnector serverConnector;
    private String key;
    private String queueKey;

    public MessageSender() {
        this.key = "message";
        this.queueKey = "queueMessages";
        serverConnector = new TFWServerConnector();
    }

    /** Connect to the TFWServer. */
    public void connect() {
        serverConnector.connect();
    }

    /** Close connecion with the TFWServer. */
    public void close() {
        serverConnector.close();
    }

    /** 
      * Sends a message to the key specified in the constructor.
      * @param originator name of sender to be displayed on the frontend
      * @param message message to send
      */
    public void send(String originator, String message) {
        ObjectMapper mapper = new ObjectMapper();
        /* Build data field. */
        ObjectNode data = mapper.createObjectNode();

        data.put("originator", originator);
        data.put("message", message);

        /* Build message. */
        ObjectNode tfwMessage = mapper.createObjectNode();

        tfwMessage.put("key", this.key);
        tfwMessage.put("data", data);

        serverConnector.send(tfwMessage);
    }

    /**
     * Queue a list of messages to be displayed in a chatbot-like manner.
     * @param originator name of sender to be displayed on the frontend
     * @param messages list of messages to queue 
     */
    public void queueMessages(String originator, List<String> messages) {
        ObjectMapper mapper = new ObjectMapper();

        ArrayNode messageArray = createMessagesJsonArray(originator, messages);

        ObjectNode data = mapper.createObjectNode();
        data.put("messages", messageArray);

        /* Build message. */
        ObjectNode tfwMessage = mapper.createObjectNode();

        tfwMessage.put("key", this.queueKey);
        tfwMessage.put("data", data);

        serverConnector.send(tfwMessage);
    }

    /**
     * Create a JSON array out of a originator and a message queue.
     * @param originator name of sender to be displayed on the frontend
     * @param messages list of messages to queue 
     */
    ArrayNode createMessagesJsonArray(String originator, List<String> messages) {
        ObjectMapper mapper = new ObjectMapper();

        ArrayNode messageArray = mapper.createArrayNode();

        for(String message : messages) {
            ObjectNode messageNode = mapper.createObjectNode();
            messageNode.put("originator", originator);
            messageNode.put("message", message);

            messageArray.add(messageNode);
        }

        return messageArray; 
    }
    
    public void writeToConsole(String message) {
        ObjectMapper mapper = new ObjectMapper();
        /* Build data field. */
        ObjectNode data = mapper.createObjectNode();

        data.put("command", "write");
        data.put("content", message);

        /* Build message. */
        ObjectNode tfwMessage = mapper.createObjectNode();

        tfwMessage.put("key", "console");
        tfwMessage.put("data", data);

        serverConnector.send(tfwMessage);
    }
}
