'use strict';
let TFWConnector = require('./TFWConnector');


class MessageSender{


    constructor(connector){
        if(connector instanceof TFWConnector){
            this.connector = connector;
        }else{
            console.error("MessageSender require an instance of TFWConnector!");
        }
    }


    send(key, data){
        let messageToSend = createTFWMessage(data);
        this.connector.pushSocket.send(messageToSend);
    }


    sendMessage(message, name = "avataobot") {
        let key = "message";
        let data = {
            key: key,
            data: {
                originator: name,
                message: message
            }
        };
        let messageToSend = createTFWMessage(data);
        this.connector.pushSocket.send(messageToSend);
    }


    sendMessages(messages, name = "avataobot"){
        let key = "queueMessages";
        let messagesToSend = [];
        messages.forEach(message=>{
           messagesToSend.push({originator: name, message: message});
        });
        let data = {
            key: key,
            data: {messages: messagesToSend}
        };
        let messageToSend = createTFWMessage(data);
        this.connector.pushSocket.send(messageToSend);
    }


    sendToEventHandlers(message){
        let key = "mirror";
        let data = {
            key: key,
            data: message
        };
        let messageToSend = createTFWMessage(data);
        this.connector.pushSocket.send(messageToSend);
    }

}


let createTFWMessage = (message) => {
    return [Buffer.from(message.key), Buffer.from(JSON.stringify(message))]
};

module.exports = MessageSender;