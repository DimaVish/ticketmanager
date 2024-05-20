package com.smartticket.ticketmanager.service;

import com.smartticket.ticketmanager.dto.UserDTO;
import com.smartticket.ticketmanager.repository.UserRepository;
import com.smartticket.ticketmanager.repository.entities.Role;
import com.smartticket.ticketmanager.repository.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final UserRepository userRepository;
    private final UserService userService;

    // Create a new passenger user
    public User createPassenger(UserDTO userDTO) {
        return userService.createUser(userDTO, Role.PASSENGER);
    }

    // Get all passengers
    public List<User> getAllPassengers() {
        return userRepository.findByRole(Role.PASSENGER);
    }

    // Get a specific passenger by ID
    public User getPassengerById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() != Role.PASSENGER) {
            throw new RuntimeException("User is not a passenger");
        }
        return user;
    }
}
