'use strict';
let TFWConnector = require('./TWFConnector');


class MessageSender{

    constructor(connector){
        if(connector instanceof TFWConnector){
            this.connector = connector;
        }else{
            console.error("MessageSender require an instance of TFWConnector!");
        }
    }

    send(key, data){
        let messageToSend = createTFWMessage(key, data);
        this.connector.pushSocket.send(messageToSend);
    }

    sendMessage(message, name = "avataobot") {
        let key = "message";
        let data = {
            key: key,
            data: {
                originator: name,
                timestamp: Date.now(),
                message: message
            }
        };
        let messageToSend = createTFWMessage(key, data);
        this.connector.pushSocket.send(messageToSend);
    }

    sendMessageQue(messages){
        if(Array.isArray(messages) && messages.length > 0){
            messages.forEach(message=>{
                console.log("Sending message: " + message);
                setTimeout(this.sendMessage(message), 1000);
            });
        }
    }

    sendToEventHandlers(message){
        let key = "mirror";
        let data = {
            key: key,
            data: message
        };
        let messageToSend = createTFWMessage(key, data);
        this.connector.pushSocket.send(messageToSend);
    }

}

let createTFWMessage = (key, message) => {
    return [Buffer.from(key), Buffer.from(JSON.stringify(message))]
};

module.exports = MessageSender;