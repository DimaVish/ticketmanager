package com.smartticket.ticketmanager.controller;

import com.smartticket.ticketmanager.dto.UserDTO;
import com.smartticket.ticketmanager.repository.entities.User;
import com.smartticket.ticketmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserById(((User) authentication.getPrincipal()).getId());
        return ResponseEntity.ok(currentUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == principal.id")
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUserInfoById(@PathVariable Long userId, @RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUserInfo(userId, userDTO));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == principal.id")
    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

}
