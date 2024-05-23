package com.smartticket.ticketmanager.service;

import com.smartticket.ticketmanager.dto.TicketDTO;
import com.smartticket.ticketmanager.exception.BusinessException;
import com.smartticket.ticketmanager.mappper.TicketManagerMapper;
import com.smartticket.ticketmanager.repository.TicketRepository;
import com.smartticket.ticketmanager.repository.entities.Ticket;
import com.smartticket.ticketmanager.repository.entities.Trip;
import com.smartticket.ticketmanager.repository.entities.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final TripService tripService;
    private final TicketManagerMapper mapper;
    private final QRCodeService qrCodeService;

    public TicketDTO purchaseTicket(TicketDTO ticketDTO) {

        User user = userService.findUserById(ticketDTO.getUserId());
        Trip trip = tripService.findTripById(ticketDTO.getTripId());

        if (trip == null) {
            throw new BusinessException(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "There is no available trip, please create a new trip before purchasing this ticket");
        }

        if (!trip.getPassenger().getId().equals(user.getId())) {
            throw new AccessDeniedException("You can not purchase ticket for current trip, please contact your system admin");
        }

        if (trip.getTicket() != null) {
            throw new BusinessException(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "You already purchased ticket for current trip, please create a new trip before purchasing a new ticket");
        }

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setTrip(trip);
        ticket.setPurchaseTime(ticketDTO.getPurchaseDate());
        ticket.setExpireTime(ticketDTO.getExpireTime());
        ticket.setUsed(false);
        //Call to your QR code service and set it
        ticket.setQrCode(ticketDTO.getQrCode());
        Ticket savedTicket = ticketRepository.save(ticket);
        trip.setTicket(savedTicket);
        tripService.updateTripWithTicket(trip);

        return mapper.toTicketDTO(savedTicket);
    }

    public TicketDTO updateTicket(Long ticketId, TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        ticket.setPurchaseTime(ticketDTO.getPurchaseDate());
        if (ticketDTO.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Date cannot be in the past");
        }
        ticket.setExpireTime(ticketDTO.getExpireTime());
        ticket.setQrCode(ticketDTO.getQrCode());
        return mapper.toTicketDTO(ticketRepository.save(ticket));
    }

    public void deleteTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        ticketRepository.delete(ticket);
    }

    public TicketDTO getTicketById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        return mapper.toTicketDTO(ticket);
    }

    public List<TicketDTO> getAllTickets() {
        return mapper.toTicketDTOList(ticketRepository.findAll());
    }

    public List<TicketDTO> getTicketsForPassenger(Long userId) {
        User user = userService.findUserById(userId);
        return mapper.toTicketDTOList(ticketRepository.findByUser(user));
    }

    public TicketDTO useTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        if (ticket.isUsed()) {
            throw new BusinessException(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "This ticket already used");
        }
        ticket.setExpireTime(LocalDateTime.now().plusHours(1));
        ticket.setQrCode(generateQrCode(ticket));
        ticket.setUsed(true);
        Ticket savedTicket = ticketRepository.save(ticket);
        return mapper.toTicketDTO(savedTicket);
    }

    @SneakyThrows
    private String generateQrCode(Ticket ticket) {
        byte[] bytes = qrCodeService.generateQRCodeImage(ticket.getTrip().getRoute());
        String stringQRcode = Arrays.toString(bytes);
        return "QR_CODE_" + stringQRcode;
    }
}

