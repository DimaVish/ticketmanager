package com.smartticket.ticketmanager.service;

import com.smartticket.ticketmanager.repository.FineRepository;
import com.smartticket.ticketmanager.repository.UserRepository;
import com.smartticket.ticketmanager.repository.entities.Fine;
import com.smartticket.ticketmanager.repository.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FineService {

    private final FineRepository fineRepository;
    private final UserRepository userRepository;

    public List<Fine> getFinesForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return fineRepository.findByUser(user);
    }

    public Fine createFine(Long userId, BigDecimal value) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Fine fine = new Fine();
        fine.setUser(user);
        fine.setValue(value);
        fine.setPaid(false); // Initially, the fine is not paid
        return fineRepository.save(fine);
    }

    public Fine payFine(Long fineId, String paymentMethod) {
        Fine fine = fineRepository.findById(fineId).orElseThrow(() -> new RuntimeException("Fine not found"));
        fine.setPaid(true);
        fine.setPaymentMethod(paymentMethod);
        return fineRepository.save(fine);
    }
}