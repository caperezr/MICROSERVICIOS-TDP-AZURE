package com.cperez.examenmartes.service;

import com.cperez.examenmartes.dto.MovementReportDTO;
import com.cperez.examenmartes.model.MovementReport;

import java.util.List;

public interface ESBEventService {
    List<MovementReport> findAllMovementReports();
}
