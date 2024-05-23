package com.smartticket.ticketmanager.controller;

import com.smartticket.ticketmanager.dto.RegisterUserDto;
import com.smartticket.ticketmanager.dto.UserDTO;
import com.smartticket.ticketmanager.service.FiscalService;
import com.smartticket.ticketmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fiscals")
public class FiscalController {

    private final FiscalService fiscalService;
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> createFiscal(@RequestBody @Valid RegisterUserDto userDTO) {
        return ResponseEntity.ok(fiscalService.createFiscal(userDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllFiscals() {
        return ResponseEntity.ok(fiscalService.getAllFiscals());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getFiscalById(@PathVariable Long userId) {
        return ResponseEntity.ok(fiscalService.getFiscalById(userId));
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('FISCAL') and #userId == principal.id)")
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateFiscalInfo(@PathVariable Long userId, @RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUserInfo(userId, userDTO));
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('FISCAL') and #userId == principal.id)")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteFiscal(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
