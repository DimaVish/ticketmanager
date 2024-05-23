package com.smartticket.ticketmanager.controller;

import com.smartticket.ticketmanager.dto.RegisterUserDto;
import com.smartticket.ticketmanager.repository.entities.User;
import com.smartticket.ticketmanager.security.dto.AuthRequestDTO;
import com.smartticket.ticketmanager.security.dto.JwtResponseDTO;
import com.smartticket.ticketmanager.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> authenticate(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        JwtResponseDTO jwtResponseDTO = authenticationService.authenticate(authRequestDTO);
        return ResponseEntity.ok(jwtResponseDTO);
    }
}
