package com.smartticket.ticketmanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FineDTO {
    @NotNull
    private Long userId;
    @NotNull
    private BigDecimal value;
    private String paymentMethod;
    @NotNull
    private LocalDateTime deadlinePaymentDate;
}
