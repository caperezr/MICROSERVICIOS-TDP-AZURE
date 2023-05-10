package com.cperez.examenmartes.controller;

import com.cperez.examenmartes.model.MovementReport;
import com.cperez.examenmartes.service.ConvertMovementReportService;
import com.cperez.examenmartes.service.ESBEventService;
import com.cperez.examenmartes.service.ProducerEventHubService;
import com.cperez.examenmartes.service.impl.ConvertMovementReportServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/movements")
@RequiredArgsConstructor
public class MovementReportController {
    @Autowired
    private ESBEventService esbEventService;
    @Autowired
    private ConvertMovementReportService convertMovementReportService;

    @Autowired
    private ProducerEventHubService producerEventHubService;


    @GetMapping
    public ResponseEntity<List<MovementReport>> finfAllMovementReports() {
        try {
            List<MovementReport> movementReports = esbEventService.findAllMovementReports();
            return ResponseEntity.status(HttpStatus.OK).body(movementReports);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/eventhub/{message}")
    public ResponseEntity<String> getMessage(@PathVariable String message) {
        try {
            producerEventHubService.emitNext(message);
            return ResponseEntity.status(HttpStatus.OK).body("Event Hub Enviado");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public String upload(@RequestParam("file")MultipartFile multipartFile){
        try {
            File file = convertMultipartFileToFile(multipartFile);
            convertMovementReportService.processJsonFile(file);
            file.delete();
            return "Cargó Exitosamente";
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return "Error";
            //return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/uploadFile")
    @ResponseStatus(HttpStatus.OK)
    public String uploadFile(@RequestParam("file")MultipartFile multipartFile){
        try {
            File file = convertMultipartFileToFile(multipartFile);
            producerEventHubService.emitNextFile(file);
            return "Cargó Exitosamente";
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return "Error";
            //return ResponseEntity.internalServerError().build();
        }
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        }
        return file;
    }



}
