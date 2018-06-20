'use strict';
const zmq = require('zeromq'),
    subSock = zmq.socket('sub'),
    pushSock = zmq.socket('push');

const SUB_PORT = process.env.TFW_PUBLISHER_PORT || 7654,
    PUSH_PORT = process.env.TFW_RECEIVER_PORT || 8765;

const SUB_ADDRESS = "tcp://localhost:" + SUB_PORT,
      PUSH_ADDRESS = "tcp://localhost:" + PUSH_PORT;


class TFWConnector {

    constructor() {
        this.pushSocket = pushSock.connect(PUSH_ADDRESS);
        this.subSocket = subSock.connect(SUB_ADDRESS);
        console.log("Push socket connected to: " + PUSH_ADDRESS + "\n" + "Subscribe socket connected to: " + SUB_ADDRESS);
    }

    subscribe(key) {
        this.subSocket.subscribe(key);
    }

}


module.exports = TFWConnector;