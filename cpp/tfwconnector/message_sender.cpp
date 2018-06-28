/* 
 * Copyright (C) 2018 Avatao.com Innovative Learning Kft.
 * All Rights Reserved. See LICENSE file for details.
 */

#include "message_sender.hpp"

#include <utility>
#include <boost/property_tree/ptree.hpp>

using boost::property_tree::ptree;
using tfwconnector::MessageSender;

MessageSender::MessageSender() : key("message"), queue_key("queueMessages") {}

void MessageSender::send(std::string originator, std::string message) {
    /* Make data field. */
    ptree data;
    data.put("originator", originator);
    data.put("message", message);

    ptree tfw_message;
    tfw_message.put("key", key);
    tfw_message.put_child("data", data);

    server_connector.send(tfw_message);
}

void MessageSender::queue_messages(std::string originator, std::vector<std::string> messages) {
    ptree message_array = create_messages_json_array(originator, messages);

    ptree messages_node;
    messages_node.put_child("messages", message_array);

    ptree tfw_message;
    tfw_message.put("key", queue_key);
    tfw_message.put_child("data", messages_node);

    server_connector.send(tfw_message);
}

ptree MessageSender::create_messages_json_array(std::string originator,
                                                std::vector<std::string> messages) {
    ptree message_array;

    for(auto message : messages) {
        ptree message_object;
        message_object.put("originator", originator);
        message_object.put("message", message);

        /* 
         * To make an array in p_tree, we have to push key-value pairs 
         * with empty keys into the node.
         * For more info read https://www.boost.org/doc/libs/1_67_0/doc/html/property_tree/parsers.html .
         */
        message_array.push_back(std::make_pair("", message_object));
    }

    return message_array;
}