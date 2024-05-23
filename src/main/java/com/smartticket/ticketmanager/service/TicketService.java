package com.smartticket.ticketmanager.service;

import com.smartticket.ticketmanager.dto.TicketDTO;
import com.smartticket.ticketmanager.repository.TicketRepository;
import com.smartticket.ticketmanager.repository.entities.Ticket;
import com.smartticket.ticketmanager.repository.entities.Trip;
import com.smartticket.ticketmanager.repository.entities.User;
import com.smartticket.ticketmanager.security.model.CustomUserDetails;
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
    private final UserService userService;
    private final TripService tripService;
    private final QRCodeService qrCodeService;

    public Ticket purchaseTicket(TicketDTO ticketDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userService.findUserById(customUserDetails.getId());
        Trip trip = tripService.findTripById(ticketDTO.getTicketId());

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setTrip(trip);
        ticket.setPurchaseDate(ticketDTO.getPurchaseDate());
        ticket.setExpireTime(ticketDTO.getExpireTime());
        //Call to your QR code service and set it
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
        User user = userService.findUserById(userId);
        return ticketRepository.findByUser(user);
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

//    private User getAuthenticatedUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return userRepository.findByUsername(authentication.getName());
//    }
}

