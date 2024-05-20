package com.smartticket.ticketmanager.service;

import com.smartticket.ticketmanager.dto.UserDTO;
import com.smartticket.ticketmanager.repository.UserRepository;
import com.smartticket.ticketmanager.repository.entities.Role;
import com.smartticket.ticketmanager.repository.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserDTO userDTO, Role role) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setPhone(userDTO.getPhone());
        user.setRole(role);
        return userRepository.save(user);
    }

    public List<User> findAllUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // Update user info for any user
    public User updateUserInfo(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the authenticated user is allowed to update this user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = userRepository.findByUsername(authentication.getName());

        if (!authenticatedUser.getId().equals(user.getId()) && !authenticatedUser.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("You are not authorized to update this user");
        }

        if (userDTO.getName() != null && !userDTO.getName().isEmpty()) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getPhone() != null && !userDTO.getPhone().isEmpty()) {
            user.setPhone(userDTO.getPhone());
        }

        return userRepository.save(user);
    }

    // Delete any user
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the authenticated user is allowed to delete this user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = userRepository.findByUsername(authentication.getName());

        if (!authenticatedUser.getId().equals(user.getId()) && !authenticatedUser.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("You are not authorized to delete this user");
        }

        userRepository.delete(user);
    }


}
