/* 
 * Copyright (C) 2018 Avatao.com Innovative Learning Kft.
 * All Rights Reserved. See LICENSE file for details.
 */

package com.avatao.tfw.tfwconnector;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;

/*
 * TFW JSON message format
 * 
 * message:
 * {
 *     "key":     string,    # addressing
 *     "data":    {...},     # payload
 *     "trigger": string     # FSM trigger
 * }
 * 
 * ZeroMQ's sub-pub sockets use enveloped messages
 * (http://zguide.zeromq.org/page:all#Pub-Sub-Message-Envelopes)
 * and TFW also uses them internally. This means that on ZMQ sockets
 * we always send the messages key separately and then the actual
 * message (which contains the key as well) like so:
 * socket.send_multipart([message['key'], message])
 * The purpose of this module is abstracting away this low level behaviour.
 */

/**
 * A serializer class which transforms JSON objects to 
 * TFW message format and vice versa.
 */
public class Serializer {

    /**
     * Serializes a JSON message into a multipart TFW message.
     * @param message message to be serialized for TFW server
     * @return multipart TFW message
     */
    public static List<String> serializeTFWMessage(ObjectNode message) {
        return serialize(message);
    }

    /** 
     * Creates a multipart message out of a JSON Object.
     * @param data the JSON object to be serialized.
     * @return multipart message where the parts are in a List
     */
    private static List<String> serialize(ObjectNode data) {
        /* Encode key in UTF-8. */
        String rawKey = data.get("key").asText();
        String key = encodeToUtf8(rawKey);
    
        String value = data.toString();

        /* Add message parts to the list. */
        List<String> messageParts = new ArrayList<String>();
        messageParts.add(key);
        messageParts.add(value);

        return messageParts;
    }

    /** 
     *  Encodes a String to a UTF-8 encoded String.
     *  @param input String to be encoded
     *  @return UTF-8 encoded String
     */
    private static String encodeToUtf8(String input) {
        String output;

        try {
            output = new String(input.getBytes(), "UTF-8");
        } catch(UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }

        return output;
    }
}