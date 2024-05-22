package com.smartticket.ticketmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDTO {
    private Double amount;
    private String currency;
    private Long passageiroId;
    private String apiToken;
}
