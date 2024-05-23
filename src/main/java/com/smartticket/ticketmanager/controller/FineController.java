package com.smartticket.ticketmanager.controller;

import com.smartticket.ticketmanager.dto.FineDTO;
import com.smartticket.ticketmanager.dto.FineResponseDTO;
import com.smartticket.ticketmanager.service.FineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fines")
public class FineController {

    private final FineService fineService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('FISCAL')")
    @GetMapping
    public ResponseEntity<List<FineResponseDTO>> getAllFines() {
        List<FineResponseDTO> allFines = fineService.getAllFines();
        if (allFines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allFines);
    }

    @PreAuthorize("hasRole('FISCAL') or (hasRole('ROLE_PASSENGER') and #userId == principal.id)")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FineResponseDTO>> getFinesForUser(@PathVariable Long userId) {
        List<FineResponseDTO> finesForUser = fineService.getFinesForUser(userId);
        if (finesForUser.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(finesForUser);
    }

    @PreAuthorize("hasRole('ROLE_FISCAL') and #fiscalId == principal.id")
    @PostMapping("/fiscals/{fiscalId}/create")
    public ResponseEntity<FineResponseDTO> createFine(@PathVariable Long fiscalId, @RequestBody @Valid FineDTO fineDTO) {
        return ResponseEntity.ok(fineService.createFine(fiscalId, fineDTO));
    }

    @PreAuthorize("hasRole('ROLE_FISCAL')")
    @PutMapping("/{fineId}")
    public ResponseEntity<FineResponseDTO> updateFine(@PathVariable Long fineId, @RequestBody @Valid FineDTO fineDTO) {
        return ResponseEntity.ok(fineService.updateFine(fineId, fineDTO));
    }

    @PreAuthorize("hasRole('ROLE_FISCAL')")
    @DeleteMapping("/{fineId}")
    public ResponseEntity<Void> deleteFine(@PathVariable Long fineId) {
        fineService.deleteFine(fineId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @PostMapping("/pay/{fineId}")
    public ResponseEntity<FineResponseDTO> payFine(@PathVariable Long fineId, @RequestParam String paymentMethod) {
        FineResponseDTO paidFine = fineService.payFine(fineId, paymentMethod);
        return ResponseEntity.ok(paidFine);
    }
}
