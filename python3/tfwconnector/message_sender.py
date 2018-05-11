# Copyright (C) 2018 Avatao.com Innovative Learning Kft.
# All Rights Reserved. See LICENSE file for details.

from datetime import datetime

from .tfw_server_connector import TFWServerConnector


class MessageSender:
    """
    Provides a mechanism to send messages to our frontend messaging component which
    displays messages with the key "message".
    """
    def __init__(self, custom_key: str = None):
        self.server_connector = TFWServerConnector()
        self.key = custom_key or 'message'

    def send(self, originator, message):
        """
        Sends a message to the key specified in __init__.
        :param originator: name of sender to be displayed on the frontend
        :param message: message to send
        """
        data = {
            'originator': originator,
            'timestamp': datetime.now().isoformat(),
            'message': message
        }
        self.server_connector.send({'key': self.key,
                                    'data': data})
