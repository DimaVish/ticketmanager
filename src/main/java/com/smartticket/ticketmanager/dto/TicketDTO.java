package com.smartticket.ticketmanager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketDTO {
    private Long userId;
    private LocalDateTime purchaseDate;
    private LocalDateTime expireTime;
    private String qrCode;
}