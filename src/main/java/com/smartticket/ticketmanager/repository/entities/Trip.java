package com.smartticket.ticketmanager.repository.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String route;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @OneToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    @JsonBackReference
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "passenger_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference
    private User passenger;
}