package com.smartticket.ticketmanager.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FineResponseDTO {
    private Long id;
    private Long fiscalId;
    private Long passengerId;
    private BigDecimal value;
    private boolean paid;
    private String paymentMethod;
    private LocalDateTime deadlinePaymentDate;
}
