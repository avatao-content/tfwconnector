'use strict';
let MessageSender = require('./MessageSender');


class Utilities{

    constructor(messageSender){
        if(messageSender instanceof MessageSender){
            this.messageSender = messageSender;
        }else{
            console.error("Utilities class needs an instance of MessageSender!");
        }
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
        this.messageSender.send(key, data);
    }

    writeShell(command) {
        let data = {
                key: "shell",
                data:{
                    command: "write",
                    value: command
                }
        };
        this.messageSender.sendToEventHandlers(data);
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
        this.messageSender.send(key,data);
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
        this.messageSender.send(key,data);
    }


}

module.exports = Utilities;