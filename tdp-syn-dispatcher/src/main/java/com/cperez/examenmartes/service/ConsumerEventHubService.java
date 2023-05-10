package com.cperez.examenmartes.service;

import org.springframework.messaging.Message;

import java.util.function.Consumer;

public interface ConsumerEventHubService {
    //Consumer<Message<String>> consume();
    Consumer<Message<byte[]>> consume();
}
