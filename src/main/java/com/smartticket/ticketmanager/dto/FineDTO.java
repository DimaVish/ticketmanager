package com.smartticket.ticketmanager.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FineDTO {
    private Long userId;
    private BigDecimal value;
    private boolean paid;
    private String paymentMethod;
}
