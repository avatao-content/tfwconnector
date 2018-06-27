/* 
 * Copyright (C) 2018 Avatao.com Innovative Learning Kft.
 * All Rights Reserved. See LICENSE file for details.
 */

#include "serialization.hpp"

#include <stdexcept>
#include <boost/property_tree/json_parser.hpp>

std::vector<std::string> tfwconnector::serialize_TFW_message(ptree message) {
    std::string key;
    /* Check if key field exists. */
    try {
        key = message.get<std::string>("key");
    } catch(...) {
        throw std::runtime_error("Invalid message format!");
    }

    /* Convert ptree to json. */
    std::ostringstream oss;
    boost::property_tree::write_json(oss, message);
    std::string json_message = oss.str();

    /* Add message parts to the vector. */
    std::vector<std::string> message_parts;
    message_parts.push_back(key);
    message_parts.push_back(json_message);

    return message_parts;
}