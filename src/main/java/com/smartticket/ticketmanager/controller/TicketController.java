package com.smartticket.ticketmanager.controller;

import com.smartticket.ticketmanager.dto.TicketDTO;
import com.smartticket.ticketmanager.repository.entities.Ticket;
import com.smartticket.ticketmanager.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Ticket createTicket(@RequestBody @Valid TicketDTO ticketDTO) {
        return ticketService.createTicket(ticketDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{ticketId}")
    public Ticket getTicketById(@PathVariable Long ticketId) {
        return ticketService.getTicketById(ticketId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{ticketId}")
    public Ticket updateTicket(@PathVariable Long ticketId, @RequestBody @Valid TicketDTO ticketDTO) {
        return ticketService.updateTicket(ticketId, ticketDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{ticketId}")
    public void deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
    }

    @PreAuthorize("hasRole('PASSENGER') ")
    @PostMapping("/purchase")
    public Ticket purchaseTicket(@RequestBody @Valid TicketDTO ticketDTO) {
        return ticketService.purchaseTicket(ticketDTO);
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @GetMapping("/user/{userId}")
    public List<Ticket> getTicketsForPassenger(@PathVariable Long userId) {
        return ticketService.getTicketsForPassenger(userId);
    }
}
