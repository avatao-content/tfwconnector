/* 
 * Copyright (C) 2018 Avatao.com Innovative Learning Kft.
 * All Rights Reserved. See LICENSE file for details.
 */

#include "tfw_server_connector.hpp"
#include "serialization.hpp"

#include <cstdlib>
#include <string>
#include <vector>
#include <sstream>
#include <stdexcept>

using tfwconnector::TFWServerConnector;

TFWServerConnector::TFWServerConnector() : context(1), push_socket(context, ZMQ_PUSH) {
    std::string enviroment = std::getenv("TFW_RECEIVER_PORT");
    std::string endpoint = "tcp://127.0.0.1:" + enviroment;
    push_socket.connect(endpoint);
}

void TFWServerConnector::send_to_eventhandler(ptree message) {
    ptree nested_message;
    /* Check if key field exists. */
    try {
        nested_message.put("key", message.get<std::string>("key"));
        nested_message.put_child("data", message.get_child("data"));
    } catch(...) {
        throw std::runtime_error("Invalid message format!");
    }

    /* Mirror original message. */
    ptree eventhandler_message;
    eventhandler_message.put("key", "mirror");
    eventhandler_message.put_child("data", nested_message);

    send(eventhandler_message);
}

void TFWServerConnector::send(ptree message) {
    auto message_parts = serialize_TFW_message(message);
    send_multipart_zmq_message(message_parts);
}

void TFWServerConnector::send_multipart_zmq_message(std::vector<std::string> multipart_message) {
    /* SNDMORE flag until the last part of the message. */
    for(int i = 0; i < multipart_message.size() - 1; i++) {
        auto message_part = multipart_message[i];
        push_socket.send(message_part.data(), message_part.size(), ZMQ_SNDMORE);
    }

    auto last_part = multipart_message[multipart_message.size() - 1];
    push_socket.send(last_part.data(), last_part.size());
}