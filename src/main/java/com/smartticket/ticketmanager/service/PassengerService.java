package com.smartticket.ticketmanager.service;

import com.smartticket.ticketmanager.repository.UserRepository;
import com.smartticket.ticketmanager.repository.entities.Role;
import com.smartticket.ticketmanager.repository.entities.User;
import com.smartticket.ticketmanager.dto.RegisterUserDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final UserRepository userRepository;
    private final UserService userService;

    // Create a new passenger user
    public User createPassenger(@Valid RegisterUserDto userDTO) {
        return userService.createUser(userDTO, Role.ROLE_PASSENGER);
    }

    // Get all passengers
    public List<User> getAllPassengers() {
        return userRepository.findByRole(Role.ROLE_PASSENGER);
    }

    // Get a specific passenger by ID
    public User getPassengerById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
