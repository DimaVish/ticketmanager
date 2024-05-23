package com.smartticket.ticketmanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketDTO {
    @NotNull
    private Long userId;
    @NotNull
    private Long ticketId;
    @NotNull
    private LocalDateTime purchaseDate;
    @NotNull
    private LocalDateTime expireTime;
    private String qrCode;
}