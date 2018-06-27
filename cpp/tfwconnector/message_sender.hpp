/* 
 * Copyright (C) 2018 Avatao.com Innovative Learning Kft.
 * All Rights Reserved. See LICENSE file for details.
 */

#ifndef MESSAGE_SENDER_H
#define MESSAGE_SENDER_H

#include "tfw_server_connector.hpp"

#include <string>

namespace tfwconnector {
    /**
     *  Provides a mechanism to send messages to our frontend messaging component which
     *  displays messages with the key "message".
     */
    class MessageSender {
      private:
        TFWServerConnector server_connector;
        std::string key;
      public:
        /**
         *  @param custom_key The key the MessageSender sends the messages with
         *  (default value: "message").
         */
        MessageSender(std::string custom_key = "message");
        /** 
          * Send a message with the key specified in the constructor.
          * @param originator name of sender to be displayed on the frontend
          * @param message message to send
          */
        void send(std::string originator, std::string message);
    };
};

#endif