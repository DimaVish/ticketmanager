package com.smartticket.ticketmanager.controller;

import com.smartticket.ticketmanager.dto.FineDTO;
import com.smartticket.ticketmanager.repository.entities.Fine;
import com.smartticket.ticketmanager.service.FineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Fine>> getAllFines() {
        return ResponseEntity.ok(fineService.getAllFines());
    }

    @PreAuthorize("hasRole('FISCAL')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Fine>> getFinesForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(fineService.getFinesForUser(userId));
    }

    @PreAuthorize("hasRole('ROLE_FISCAL') and #fiscalId == principal.id")
    @PostMapping("/{fiscalId}/create")
    public ResponseEntity<Fine> createFine(@PathVariable Long fiscalId, @RequestBody FineDTO fineDTO) {
        return ResponseEntity.ok(fineService.createFine(fiscalId, fineDTO));
    }

    @PreAuthorize("hasRole('ROLE_FISCAL') and #fiscalId == principal.id")
    @PutMapping("/{fineId}")
    public ResponseEntity<Fine> updateFine(@PathVariable Long fineId, @RequestBody @Valid FineDTO fineDTO) {
        return ResponseEntity.ok(fineService.updateFine(fineId, fineDTO));
    }

    @PreAuthorize("hasRole('ROLE_FISCAL') and #fiscalId == principal.id")
    @DeleteMapping("/{fineId}")
    public ResponseEntity<Void> deleteFine(@PathVariable Long fineId) {
        fineService.deleteFine(fineId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @PostMapping("/pay/{fineId}")
    public ResponseEntity<Fine> payFine(@PathVariable Long fineId, @RequestParam String paymentMethod) {
        Fine paidFine = fineService.payFine(fineId, paymentMethod);
        return ResponseEntity.ok(paidFine);
    }
}
