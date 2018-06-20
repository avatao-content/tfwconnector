/* 
 * Copyright (C) 2018 Avatao.com Innovative Learning Kft.
 * All Rights Reserved. See LICENSE file for details.
 */

package com.avatao.tfw.tfwconnector;

import java.util.Date;
import java.util.TimeZone;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.avatao.tfw.tfwconnector.TFWServerConnector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 *  Provides a mechanism to send messages to our frontend messaging component which
 *  displays messages with the key "message".
 */
public class MessageSender {
    private TFWServerConnector serverConnector;
    private String key;

    public MessageSender() {
        this.key = "message";
        serverConnector = new TFWServerConnector();
    }

    /**
     *  @param customKey The key the MessageSender sends the messages with.
     */
    public MessageSender(String customKey) {
        this.key = customKey;
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
        data.put("timestamp", getIsoDateTime());
        data.put("message", message);

        /* Build message. */
        ObjectNode tfwMessage = mapper.createObjectNode();

        tfwMessage.put("key", this.key);
        tfwMessage.put("data", data);

        serverConnector.send(tfwMessage);
    }

    /**
      * @return current date time in ISO format
      */
    private String getIsoDateTime() {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        dateFormat.setTimeZone(timeZone);

        String isoFormat = dateFormat.format(new Date());
        return isoFormat;
    }

}