package com.smartticket.ticketmanager.controller;

import com.smartticket.ticketmanager.dto.RegisterUserDto;
import com.smartticket.ticketmanager.dto.UserDTO;
import com.smartticket.ticketmanager.service.PassengerService;
import com.smartticket.ticketmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createPassenger(@RequestBody @Valid RegisterUserDto userDTO) {
        return ResponseEntity.ok(passengerService.createPassenger(userDTO));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getPassengerById(@PathVariable Long userId) {
        return ResponseEntity.ok(passengerService.getPassengerById(userId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{passengerId}")
    public ResponseEntity<UserDTO> updatePassengerInfo(@PathVariable Long passengerId, @RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUserInfo(passengerId, userDTO));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

}
