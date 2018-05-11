# Copyright (C) 2018 Avatao.com Innovative Learning Kft.
# All Rights Reserved. See LICENSE file for details.
"""
TFW JSON message format

message:
{
    "key":     string,    # addressing
    "data":    {...},     # payload
    "trigger": string     # FSM trigger
}

ZeroMQ's sub-pub sockets use enveloped messages
(http://zguide.zeromq.org/page:all#Pub-Sub-Message-Envelopes)
and TFW also uses them internally. This means that on ZMQ sockets
we always send the messages key separately and then the actual
message (which contains the key as well) like so:

socket.send_multipart([message['key'], message])

The purpose of this module is abstracting away this low level behaviour.
"""

import json


def serialize_tfw_msg(message):
    """
    Create TFW multipart data from message dict
    """
    return _serialize_all(message['key'], message)


def _serialize_all(*args):
    return tuple(_serialize_single(arg) for arg in args)


def _serialize_single(data):
    """
    Return input as bytes
    (serialize input if it is JSON)
    """
    if not isinstance(data, str):
        data = json.dumps(data)
    return _encode_if_needed(data)


def _encode_if_needed(value):
    """
    Return input as bytes
    (encode if input is string)
    """
    if isinstance(value, str):
        value = value.encode('utf-8')
    return value
