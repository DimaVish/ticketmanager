package com.smartticket.ticketmanager.controller;

import com.smartticket.ticketmanager.dto.UserDTO;
import com.smartticket.ticketmanager.repository.entities.User;
import com.smartticket.ticketmanager.service.FiscalService;
import com.smartticket.ticketmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public User createFiscal(@RequestBody @Valid UserDTO userDTO) {
        return fiscalService.createFiscal(userDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<User> getAllFiscals() {
        return fiscalService.getAllFiscals();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public User getFiscalById(@PathVariable Long userId) {
        return fiscalService.getFiscalById(userId);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('FISCAL') and #userId == principal.id)")
    @PutMapping("/{userId}")
    public User updateFiscalInfo(@PathVariable Long userId, @RequestBody @Valid UserDTO userDTO) {
        return userService.updateUserInfo(userId, userDTO);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('FISCAL') and #userId == principal.id)")
    @DeleteMapping("/{userId}")
    public void deleteFiscal(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
