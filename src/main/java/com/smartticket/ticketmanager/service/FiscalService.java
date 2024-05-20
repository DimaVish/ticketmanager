package com.smartticket.ticketmanager.service;

import com.smartticket.ticketmanager.dto.UserDTO;
import com.smartticket.ticketmanager.repository.entities.Role;
import com.smartticket.ticketmanager.repository.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FiscalService {

    private final UserService userService;

    // Create a new fiscal user
    public User createFiscal(UserDTO userDTO) {
        return userService.createUser(userDTO, Role.FISCAL);
    }

    // Get all fiscals
    public List<User> getAllFiscals() {
        return userService.findAllUsersByRole(Role.FISCAL);
    }

    // Get a specific fiscal by ID
    public User getFiscalById(Long userId) {
        return userService.findUserById(userId).orElse(null);
    }
}
