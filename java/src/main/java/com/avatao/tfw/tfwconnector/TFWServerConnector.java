/* 
 * Copyright (C) 2018 Avatao.com Innovative Learning Kft.
 * All Rights Reserved. See LICENSE file for details.
 */

package com.avatao.tfw.tfwconnector;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.zeromq.ZMQ;

import com.avatao.tfw.tfwconnector.Serializer;

/**
 * Class capable of sending messages to the TFW server and event handlers.
 */
public class TFWServerConnector {
    private ZMQ.Context context;
    private ZMQ.Socket pushSocket;

    /** URL to the socket responsible for TFW communication. */
    private String socketUrl;

    public TFWServerConnector() {
        this.context = ZMQ.context(1);
        this.pushSocket = context.socket(ZMQ.PUSH);
        this.socketUrl = "tcp://localhost:" + System.getenv("TFW_RECEIVER_PORT");
    }

    /** Connect to TFWServer. */
    public void connect() {
        pushSocket.connect(this.socketUrl);
    }

    /** Close connection with TFWServer. */
    public void close() {
        pushSocket.close();
        context.term();
    }


    /**
     * Send a message to an event handler.
     * This envelopes the desired message in the 'data' field of the message to
     * TFWServer, which will mirror it to event handlers.
     * @param message JSON message you want to send
     */
    public void sendToEventhandler(ObjectNode message) {
        ObjectMapper mapper = new ObjectMapper();
	    ObjectNode eventhandlerMessage = mapper.createObjectNode();
        ObjectNode nestedMessage = mapper.createObjectNode();

        nestedMessage.put("key", message.get("key"));
        nestedMessage.put("data", message.get("data"));

        /* Mirror original message. */
        eventhandlerMessage.put("key", "mirror");
        eventhandlerMessage.put("data", nestedMessage);

        send(eventhandlerMessage);
    }

    /**
     * Send a message to the TFW server.
     * @param message JSON message you want to send
     */
    public void send(ObjectNode message) {
        /* Send Multipart request. */
        List<String> messageParts = Serializer.serializeTFWMessage(message);

        for(int i = 0; i < messageParts.size() - 1; i++) {
            pushSocket.sendMore(messageParts.get(i));    
        }

        pushSocket.send(messageParts.get(messageParts.size() - 1));
    }
}