package com.smartticket.ticketmanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TripDTO {

    @NotNull
    private String route;
    @NotNull
    private LocalDateTime dateTime;
    @NotNull
    private Long passengerId;
}
