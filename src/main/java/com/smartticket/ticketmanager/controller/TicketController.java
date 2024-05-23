package com.smartticket.ticketmanager.controller;

import com.smartticket.ticketmanager.dto.TicketDTO;
import com.smartticket.ticketmanager.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PASSENGER') and #ticketDTO.userId == principal.id")
    @PostMapping("/purchase")
    public ResponseEntity<TicketDTO> purchaseTicket(@RequestBody @Valid TicketDTO ticketDTO) {
        return ResponseEntity.ok(ticketService.purchaseTicket(ticketDTO));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{ticketId}")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable Long ticketId, @RequestBody @Valid TicketDTO ticketDTO) {
        return ResponseEntity.ok(ticketService.updateTicket(ticketId, ticketDTO));
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @PatchMapping("/{ticketId}/use")
    public ResponseEntity<TicketDTO> useTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(ticketService.useTicket(ticketId));
    }

    @PreAuthorize("hasRole('ROLE_PASSENGER') and #userId == principal.id")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketDTO>> getTicketsForPassenger(@PathVariable Long userId) {
        return ResponseEntity.ok(ticketService.getTicketsForPassenger(userId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long ticketId) {
        return ResponseEntity.ok(ticketService.getTicketById(ticketId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.ok().build();
    }


}
