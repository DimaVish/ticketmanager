package com.smartticket.ticketmanager.repository;

import com.smartticket.ticketmanager.repository.entities.Ticket;
import com.smartticket.ticketmanager.repository.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUser(User user);

    @Transactional
    void deleteByTripId(Long tripId);
}
