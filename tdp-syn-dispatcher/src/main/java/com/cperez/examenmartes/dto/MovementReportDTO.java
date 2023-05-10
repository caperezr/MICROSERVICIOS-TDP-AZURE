package com.cperez.examenmartes.dto;

import com.cperez.examenmartes.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementReportDTO {
    private String subscriptionId;
    private String frameworkAgreementId;
    private String paymentMethodType;
    private List<Product> products;
    private String traceId;
    private String alias;
    private String emailAddress;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ")
    //@JsonDeserialize(using = CustomDateDeserializer.class)
    private String transactionDateTime;
    private String creationDate;
    private String subscriptionStatus;
    private boolean activation;
    private boolean isDeleted;
}

