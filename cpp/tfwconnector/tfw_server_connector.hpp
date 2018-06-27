/* 
 * Copyright (C) 2018 Avatao.com Innovative Learning Kft.
 * All Rights Reserved. See LICENSE file for details.
 */

#ifndef TFW_SERVER_CONNECTOR_H
#define TFW_SERVER_CONNECTOR_H

#include <zmq.hpp>
#include <boost/property_tree/ptree.hpp>

using boost::property_tree::ptree;

namespace tfwconnector {
    /**
     * Class capable of sending messages to the TFW server and event handlers.
     */
    class TFWServerConnector {
      private:
        zmq::context_t context;
        zmq::socket_t push_socket;

        /**
         * Send a multipart message through the push socket.
         * @param multipart_message message to be sent 
         */
        void send_multipart_zmq_message(std::vector<std::string> multipart_message);

      public:
        TFWServerConnector();
        /**
         * Send a message to an event handler.
         * This envelopes the desired message in the 'data' field of the message to
         * TFWServer, which will mirror it to event handlers.
         * @param message JSON message you want to send
         */
        void send_to_eventhandler(ptree message);
        /**
         * Send a message to the TFW server.
         * @param message JSON message you want to send
         */
        void send(ptree message);
    };
};
#endif