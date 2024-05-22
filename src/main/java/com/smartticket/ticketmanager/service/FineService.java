package com.smartticket.ticketmanager.service;

import com.smartticket.ticketmanager.dto.FineDTO;
import com.smartticket.ticketmanager.repository.FineRepository;
import com.smartticket.ticketmanager.repository.UserRepository;
import com.smartticket.ticketmanager.repository.entities.Fine;
import com.smartticket.ticketmanager.repository.entities.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FineService {

    private final FineRepository fineRepository;
    private final UserRepository userRepository;

    public List<Fine> getAllFines() {
        return fineRepository.findAll();
    }

    public List<Fine> getFinesForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return fineRepository.findByUser(user);
    }

    public Fine createFine(Long fiscalId, FineDTO fineDTO) {
        User fiscal = userRepository.findById(fiscalId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        User passenger = userRepository.findById(fineDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Fine fine = new Fine();
        fine.setUser(passenger);
        fine.setValue(fineDTO.getValue());
        fine.setPaid(false); // Initially, the fine is not paid
        return fineRepository.save(fine);
    }

    public Fine updateFine(Long fineId, FineDTO fineDTO) {
        Fine fine = fineRepository.findById(fineId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        fine.setPaid(fineDTO.isPaid());
        fine.setValue(fineDTO.getValue());
        fine.setPaymentMethod(fineDTO.getPaymentMethod());
        return fineRepository.save(fine);
    }

    public void deleteFine(Long fineId) {
        fineRepository.deleteById(fineId);
    }

    public Fine payFine(Long fineId, String paymentMethod) {
        Fine fine = fineRepository.findById(fineId).orElseThrow(() -> new RuntimeException("Fine not found"));
        fine.setPaid(true);
        fine.setPaymentMethod(paymentMethod);
        return fineRepository.save(fine);
    }
}