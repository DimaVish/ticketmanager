package com.smartticket.ticketmanager.controller;

import com.smartticket.ticketmanager.dto.FineDTO;
import com.smartticket.ticketmanager.repository.entities.Fine;
import com.smartticket.ticketmanager.service.FineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fines")
public class FineController {

    private final FineService fineService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('FISCAL')")
    @GetMapping
    public List<Fine> getAllFines() {
        return fineService.getAllFines();
    }

    @PreAuthorize("hasRole('FISCAL')")
    @GetMapping("/user/{userId}")
    public List<Fine> getFinesForUser(@PathVariable Long userId) {
        return fineService.getFinesForUser(userId);
    }

    @PreAuthorize("hasRole('FISCAL')")
    @PostMapping("/create/{userId}")
    public Fine createFine(@PathVariable Long userId, @RequestParam BigDecimal value) {
        return fineService.createFine(userId, value);
    }

    @PreAuthorize("hasRole('FISCAL')")
    @PutMapping("/{fineId}")
    public Fine updateFine(@PathVariable Long fineId, @RequestBody @Valid FineDTO fineDTO) {
        return fineService.updateFine(fineId, fineDTO);
    }

    @PreAuthorize("hasRole('FISCAL')")
    @DeleteMapping("/{fineId}")
    public void deleteFine(@PathVariable Long fineId) {
        fineService.deleteFine(fineId);
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @PostMapping("/pay/{fineId}")
    public Fine payFine(@PathVariable Long fineId, @RequestParam String paymentMethod) {
        return fineService.payFine(fineId, paymentMethod);
    }
}
