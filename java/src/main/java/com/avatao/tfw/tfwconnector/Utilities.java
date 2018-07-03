package com.avatao.tfw.tfwconnector;

import com.avatao.tfw.tfwconnector.TFWServerConnector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Utilities {
    private TFWServerConnector serverConnector;

    public Utilities(TFWServerConnector serverConnector) {
        this.serverConnector = serverConnector;
    }

    /**
     * Send a trigger to the FSM to activate a transition
     * @param message transition you want to trigger
     */
    public void sendTrigger(String message) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode triggerMessage = mapper.createObjectNode();

        triggerMessage.put("key", "");
        triggerMessage.put("trigger", message);

        serverConnector.send(triggerMessage);
    }

}
