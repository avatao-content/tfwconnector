package com.avatao.tfw.tfwconnector;

import com.avatao.tfw.tfwconnector.TFWServerConnector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Utilities {
    private TFWServerConnector serverConnector;
    private ObjectMapper mapper;

    public Utilities(TFWServerConnector serverConnector) {
        mapper = new ObjectMapper();
        this.serverConnector = serverConnector;
    }

    public Utilities() {
        mapper = new ObjectMapper();
        serverConnector = new TFWServerConnector();
        serverConnector.connect();
    }

    /**
     * Send a trigger to the FSM to activate a transition
     * @param trigger transition you want to trigger
     */
    public void sendTrigger(String trigger) {
        ObjectNode triggerMessage = mapper.createObjectNode();

        triggerMessage.put("key", "");
        triggerMessage.put("trigger", trigger);

        serverConnector.send(triggerMessage);
    }

}

