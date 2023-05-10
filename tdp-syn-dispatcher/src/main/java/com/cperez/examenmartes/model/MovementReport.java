package com.cperez.examenmartes.model;

import com.cperez.examenmartes.dto.MovementReportDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Collection-ESBEvent")
public class MovementReport {
    @Id
    private String id;
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
    public MovementReport(MovementReportDTO movementReportDTO) {
        this.subscriptionId = movementReportDTO.getSubscriptionId();
        this.frameworkAgreementId = movementReportDTO.getFrameworkAgreementId();
        this.paymentMethodType = movementReportDTO.getPaymentMethodType();
        this.products = movementReportDTO.getProducts();
        this.traceId = movementReportDTO.getTraceId();
        this.alias = movementReportDTO.getAlias();
        this.emailAddress = movementReportDTO.getEmailAddress();
        this.transactionDateTime = movementReportDTO.getTransactionDateTime();
        this.creationDate = movementReportDTO.getCreationDate();
        this.subscriptionStatus = movementReportDTO.getSubscriptionStatus();
        this.activation = movementReportDTO.isActivation();
        this.isDeleted = movementReportDTO.isDeleted();
    }
}
