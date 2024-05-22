package com.smartticket.ticketmanager.controller;

import com.smartticket.ticketmanager.dto.RegisterUserDto;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public User createPassenger(@RequestBody @Valid RegisterUserDto userDTO) {
        return passengerService.createPassenger(userDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<User> getAllPassengers() {
        return passengerService.getAllPassengers();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}")
    public User getPassengerById(@PathVariable Long userId) {
        return passengerService.getPassengerById(userId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{passengerId}")
    public User updatePassengerInfo(@PathVariable Long passengerId, @RequestBody @Valid UserDTO userDTO) {
        return userService.updateUserInfo(passengerId, userDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public void deletePassenger(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

}
