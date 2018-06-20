'use strict';
const zmq = require('zeromq'),
    subSock = zmq.socket('sub'),
    pushSock = zmq.socket('push');

const subPort = process.env.TFW_PUBLISHER_PORT || 7654,
    pushPort = process.env.TFW_RECEIVER_PORT || 8765;

const subAddress = "tcp://localhost:" + subPort,
      pushAddress = "tcp://localhost:" + pushPort;


class TFWConnector {
    constructor() {
        this.pushSocket = pushSock.connect(pushAddress);
        this.subSocket = subSock.connect(subAddress);
        console.log("Push socket connected to: " + pushAddress + "\n" + "Subscribe socket connected to: " + subAddress);
    }

    subscribe(key) {
        this.subSocket.subscribe(key);
    }

    sendMessage(message, name = "avataobot") {
        let key = "message";
        let data = {
            key: key,
            data: {
                originator: name,
                timestamp: Date.now(),
                message: message
            },
            trigger: ''
        };
        let messageToSend = createTFWMessage(key, data);
        this.pushSocket.send(messageToSend);
    }

    setView(viewType) {
        let key = "dashboard";
        let data = {
            key: key,
            data: {
                command: "layout",
                value: viewType
            }
        };
        let messageToSend = createTFWMessage(key, data);
        this.pushSocket.send(messageToSend);
    }

    writeShell(command) {
        let key = "mirror";
        let data = {
            key: key,
            data: {
                key: "shell",
                data:{
                    command: "write",
                    value: command
                }
            }
        };
        let messageToSend = createTFWMessage(key, data);
        this.pushSocket.send(messageToSend);
    }

    sendToEventHandlers(message){
        let key = "mirror";
        let data = {
            key: key,
            data: message
        };
        let messageToSend = createTFWMessage(key, data);
        this.pushSocket.send(messageToSend);
    }

    stepNextState(){
        let key = "";
        let data = {
            key: key,
            data: {
                event: "Stepping FSM to next state, from node event handler"
            },
            trigger: "step_next"
        };
        let messageToSend = createTFWMessage(key, data);
        this.pushSocket.send(messageToSend);
    }

    stepToState(state){
        let key = "";
        let data = {
            key: key,
            data: {
                event: "Stepping FSM to next state, from node event handler"
            },
            trigger: "to_"+state
        };
        let messageToSend = createTFWMessage(key, data);
        this.pushSocket.send(messageToSend);
    }


}

let createTFWMessage = (key, data) => {
    return [Buffer.from(key), Buffer.from(JSON.stringify(data))]
};

module.exports = TFWConnector;