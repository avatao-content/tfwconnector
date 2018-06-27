/* 
 * Copyright (C) 2018 Avatao.com Innovative Learning Kft.
 * All Rights Reserved. See LICENSE file for details.
 */

#include "message_sender.hpp"

#include <boost/property_tree/ptree.hpp>

using boost::property_tree::ptree;
using tfwconnector::MessageSender;

MessageSender::MessageSender(std::string custom_key) : key(custom_key) {}

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
