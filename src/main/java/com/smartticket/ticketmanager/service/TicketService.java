package com.smartticket.ticketmanager.service;

import com.smartticket.ticketmanager.dto.TicketDTO;
import com.smartticket.ticketmanager.repository.TicketRepository;
import com.smartticket.ticketmanager.repository.UserRepository;
import com.smartticket.ticketmanager.repository.entities.Role;
import com.smartticket.ticketmanager.repository.entities.Ticket;
import com.smartticket.ticketmanager.repository.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public Ticket createTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setUser(userRepository.findById(ticketDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        ticket.setPurchaseDate(ticketDTO.getPurchaseDate());
        ticket.setExpireTime(ticketDTO.getExpireTime());
        ticket.setQrCode(ticketDTO.getQrCode());
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(Long ticketId, TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setPurchaseDate(ticketDTO.getPurchaseDate());
        ticket.setExpireTime(ticketDTO.getExpireTime());
        ticket.setQrCode(ticketDTO.getQrCode());
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticketRepository.delete(ticket);
    }

    public Ticket getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getTicketsForPassenger(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = userRepository.findByUsername(authentication.getName());

        if (!authenticatedUser.getId().equals(userId) && !authenticatedUser.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("You are not authorized to view these tickets");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return ticketRepository.findByUser(user);
    }

    public Ticket purchaseTicket(TicketDTO ticketDTO) {
        User authenticatedUser = getAuthenticatedUser();

        Ticket ticket = new Ticket();
        ticket.setUser(authenticatedUser);
        ticket.setPurchaseDate(LocalDateTime.now());
        // Assume ticket expires in 1 hour for simulation
        ticket.setExpireTime(LocalDateTime.now().plusHours(1));
        ticket.setQrCode(generateQrCode(ticket));

        return ticketRepository.save(ticket);
    }

    public Ticket useTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setExpireTime(LocalDateTime.now().plusHours(1));
        ticket.setQrCode(generateQrCode(ticket));
        return ticketRepository.save(ticket);
    }

    private String generateQrCode(Ticket ticket) {
        // Generate QR code logic
        return "QR_CODE_" + ticket.getId();
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName());
    }
}

