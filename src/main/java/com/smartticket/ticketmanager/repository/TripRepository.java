package com.smartticket.ticketmanager.repository;

import com.smartticket.ticketmanager.repository.entities.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {

    Optional<Trip> findByRoute(String route);

    Page<Trip> findAllByDateTime(@Param("date") Instant date, Pageable pageable);

}
