package com.cperez.examenmartes.service.impl;

import com.cperez.examenmartes.service.ProducerEventHubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Supplier;

@Slf4j
@Service
public class ProducerEventHubServiceImpl implements ProducerEventHubService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerEventHubServiceImpl.class);
    private final Sinks.Many<Message<Object>> many = Sinks.many().unicast().onBackpressureBuffer();
    @Bean
    public Supplier<Flux<Message<Object>>> supply() {
        return () -> many.asFlux()
                .doOnNext(m -> LOGGER.info("Enviar Archivo  {}", m))
                .doOnError(t -> LOGGER.error("Error encontrado", t));

    }

    @Override
    public void emitNext(String message) {
        many.emitNext(new GenericMessage<>(message), Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override
    public void emitNextFile(File file) {
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            many.emitNext(new GenericMessage<>(fileContent), Sinks.EmitFailureHandler.FAIL_FAST);
        } catch (IOException e) {
            LOGGER.error("Error al leer el archivo", e);
        }
    }
}
