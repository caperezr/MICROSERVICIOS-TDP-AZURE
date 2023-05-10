package com.cperez.examenmartes.service;

import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;

import java.io.File;
import java.util.function.Supplier;

public interface ProducerEventHubService {
    Supplier<Flux<Message<Object>>> supply();
    void emitNext(String message);
    void emitNextFile(File file);
}
