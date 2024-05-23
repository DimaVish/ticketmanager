package com.smartticket.ticketmanager.repository.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "fines")
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fiscal_id", nullable = false)
    @JsonBackReference
    private User fiscal;

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    @JsonBackReference
    private User passenger;

    @Column(nullable = false)
    private BigDecimal value;

    private boolean paid;

    private String paymentMethod;

    @Column(name = "deadline_payment_date", nullable = false)
    private LocalDateTime deadlinePaymentDate;
}
