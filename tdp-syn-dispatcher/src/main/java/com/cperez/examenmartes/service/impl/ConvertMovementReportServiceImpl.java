package com.cperez.examenmartes.service.impl;
import com.cperez.examenmartes.dto.MovementReportDTO;
import com.cperez.examenmartes.model.MovementReport;
import com.cperez.examenmartes.repository.ESBEventRepository;
import com.cperez.examenmartes.service.ConvertMovementReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import static com.azure.spring.cloud.service.implementation.eventhubs.factory.EventHubClientBuilderFactory.LOGGER;

@Service
public class ConvertMovementReportServiceImpl implements ConvertMovementReportService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ESBEventRepository esbEventRepository;

    /*@PostConstruct
    public void init() {
        processJsonFile();
    }*/

    public void processJsonFile(File file) {
        try {
            //File file = new ClassPathResource("example.json").getFile();
            MovementReportDTO[] movementReportDTOS = objectMapper.readValue(file, MovementReportDTO[].class);
            for (MovementReportDTO movementReportDTO : movementReportDTOS) {
                MovementReport movementReport = new MovementReport(movementReportDTO);
                esbEventRepository.save(movementReport);
                LOGGER.info("Guardado en la base de datos: {}", movementReportDTO.toString());
            }
            file.delete();

        } catch (IOException e) {
            LOGGER.error("Error al procesar el archivo JSON: {}", e.getMessage());
        }
    }
}
