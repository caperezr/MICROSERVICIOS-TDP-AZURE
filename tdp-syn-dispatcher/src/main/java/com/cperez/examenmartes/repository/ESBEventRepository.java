package com.cperez.examenmartes.repository;

import com.cperez.examenmartes.model.MovementReport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ESBEventRepository extends MongoRepository<MovementReport, String> {
}
