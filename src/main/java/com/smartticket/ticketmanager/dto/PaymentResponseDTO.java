package com.smartticket.ticketmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentResponseDTO {
    private String status;
    private String transactionId;
    private String message;
}
