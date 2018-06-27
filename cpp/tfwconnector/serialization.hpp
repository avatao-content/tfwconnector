/* 
 * Copyright (C) 2018 Avatao.com Innovative Learning Kft.
 * All Rights Reserved. See LICENSE file for details.
 */

#ifndef SERIALIZATION_H
#define SERIALIZATION_H

#include <vector>
#include <string>

#include <boost/property_tree/ptree.hpp>

using boost::property_tree::ptree;

/*
 * TFW JSON message format
 * 
 * message:
 * {
 *     "key":     string,    # addressing
 *     "data":    {...},     # payload
 *     "trigger": string     # FSM trigger
 * }
 * 
 * ZeroMQ's sub-pub sockets use enveloped messages
 * (http://zguide.zeromq.org/page:all#Pub-Sub-Message-Envelopes)
 * and TFW also uses them internally. This means that on ZMQ sockets
 * we always send the messages key separately and then the actual
 * message (which contains the key as well) like so:
 * socket.send_multipart([message['key'], message])
 * The purpose of this module is abstracting away this low level behaviour.
 */

namespace tfwconnector {
    /**
     * Serialize a JSON message into a multipart TFW message.
     * @param message message to be serialized for TFW server
     * @return multipart TFW message
     */
    std::vector<std::string> serialize_TFW_message(ptree message);
};

#endif