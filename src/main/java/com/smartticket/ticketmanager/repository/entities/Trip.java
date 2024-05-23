package com.smartticket.ticketmanager.repository.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String route;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @OneToOne(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "passenger_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference
    private User passenger;
}