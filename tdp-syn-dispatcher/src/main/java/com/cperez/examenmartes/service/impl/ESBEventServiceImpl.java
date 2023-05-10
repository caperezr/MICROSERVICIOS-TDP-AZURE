package com.cperez.examenmartes.service.impl;

import com.cperez.examenmartes.dto.MovementReportDTO;
import com.cperez.examenmartes.model.MovementReport;
import com.cperez.examenmartes.repository.ESBEventRepository;
import com.cperez.examenmartes.service.ESBEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ESBEventServiceImpl implements ESBEventService {
    @Autowired
    private ESBEventRepository esbEventRepository;

    @Override
    public List<MovementReport> findAllMovementReports() {
        List<MovementReport> movementReports = esbEventRepository.findAll();
        return movementReports;
    }
}
