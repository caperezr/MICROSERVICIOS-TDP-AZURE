package com.cperez.examenmartes.service.impl;

import com.cperez.examenmartes.service.ConsumerEventHubService;
import com.cperez.examenmartes.service.ConvertMovementReportService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Consumer;

@Slf4j
@Service
public class ConsumerEventHubServiceImpl implements ConsumerEventHubService {
    @Autowired
    private ConvertMovementReportService convertMovementReportService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerEventHubServiceImpl.class);
    /*@Bean
    public Consumer<Message<String>> consume() {
        return message->LOGGER.info("Nuevo Mensaje Recibido: '{}'", message.getPayload());
    }*/
    @Bean
    public Consumer<Message<byte[]>> consume() {
        return message -> {
            byte[] fileContent = message.getPayload();
            //manejar las excepciones
            try {
                long fileSize = fileContent.length;

                // Mostrar mensaje en el registro de log
                LOGGER.info("Archivo recibido - Tamaño: {} bytes", fileSize);

                File tempFile = File.createTempFile("temp", ".json");
                Files.write(tempFile.toPath(), fileContent);

                convertMovementReportService.processJsonFile(tempFile);

                // Eliminar el archivo temporal ya que no es necesario
                tempFile.delete();

            } catch (IOException e) {
                LOGGER.error("Error al procesar el archivo: {}", e.getMessage());
            }
        };
    }
}
