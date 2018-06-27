# Copyright (C) 2018 Avatao.com Innovative Learning Kft.
# All Rights Reserved. See LICENSE file for details.

from tfw.networking.event_handlers import ServerUplinkConnector


class MessageSender:
    """
    Provides mechanisms to send messages to our frontend messaging component.
    """
    def __init__(self):
        self.server_connector = ServerUplinkConnector()
        self.key = 'message'
        self.queue_key = 'queueMessages'

    def send(self, originator, message):
        """
        Sends a message.
        :param originator: name of sender to be displayed on the frontend
        :param message: message to send
        """
        data = {
            'originator': originator,
            'message': message
        }
        self.server_connector.send({
            'key': self.key,
            'data': data
        })

    def queue_messages(self, originator, messages):
        """
        Queues a list of messages to be displayed in a chatbot-like manner.
        :param originator: name of sender to be displayed on the frontend
        :param messages: list of messages to queue
        """
        data = {
            'messages': [
                {'message': message, 'originator': originator}
                for message in messages
            ]
        }
        self.server_connector.send({
            'key': self.queue_key,
            'data': data
        })

