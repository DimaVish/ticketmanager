package com.smartticket.ticketmanager.controller;

import com.smartticket.ticketmanager.dto.UserDTO;
import com.smartticket.ticketmanager.repository.entities.User;
import com.smartticket.ticketmanager.service.PassengerService;
import com.smartticket.ticketmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public User createPassenger(@RequestBody @Valid UserDTO userDTO) {
        return passengerService.createPassenger(userDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<User> getAllPassengers() {
        return passengerService.getAllPassengers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public User getPassengerById(@PathVariable Long userId) {
        return passengerService.getPassengerById(userId);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('PASSENGER') and #userId == principal.id)")
    @PutMapping("/{userId}")
    public User updatePassengerInfo(@PathVariable Long userId, @RequestBody @Valid UserDTO userDTO) {
        return userService.updateUserInfo(userId, userDTO);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('PASSENGER') and #userId == principal.id)")
    @DeleteMapping("/{userId}")
    public void deletePassenger(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
