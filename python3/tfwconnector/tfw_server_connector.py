# Copyright (C) 2018 Avatao.com Innovative Learning Kft.
# All Rights Reserved. See LICENSE file for details.

import os

import zmq

from .serialization import serialize_tfw_msg


class TFWServerConnector:
    """
    Class capable of sending messages to the TFW server and event handlers.
    """
    def __init__(self):
        self._zmq_context = zmq.Context.instance()
        self._zmq_push_socket = self._zmq_context.socket(zmq.PUSH)
        self._zmq_push_socket.connect(f'tcp://localhost:{os.environ["TFW_RECEIVER_PORT"]}')

    def send_to_eventhandler(self, message):
        """
        Send a message to an event handler.
        This envelopes the desired message in the 'data' field of the message to
        TFWServer, which will mirror it to event handlers.

        :param message: JSON message you want to send
        :param message['key']: key of event handler you want to address
        """
        nested_message = {'key': message['key'], 'data': message.pop('data')}
        message['key'] = 'mirror'
        message['data'] = nested_message
        self.send(message)

    def send(self, message):
        """
        Send a message to the TFW server

        :param message: JSON message you want to send
        """
        self._zmq_push_socket.send_multipart(serialize_tfw_msg(message))
