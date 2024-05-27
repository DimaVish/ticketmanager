package com.smartticket.ticketmanager.service;


import com.smartticket.ticketmanager.dto.RegisterUserDto;
import com.smartticket.ticketmanager.repository.UserRepository;
import com.smartticket.ticketmanager.repository.entities.User;
import com.smartticket.ticketmanager.security.JwtService;
import com.smartticket.ticketmanager.security.UserDetailsServiceImpl;
import com.smartticket.ticketmanager.security.dto.AuthRequestDTO;
import com.smartticket.ticketmanager.security.dto.JwtResponseDTO;
import com.smartticket.ticketmanager.security.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setUsername(input.getUserName());
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPhone(input.getPhone());
        user.setRole(input.getRole());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public JwtResponseDTO authenticate(AuthRequestDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(input.getUsername());

        String jwtToken = jwtService.generateToken(userDetails);

        JwtResponseDTO jwtResponseDTO = new JwtResponseDTO();
        jwtResponseDTO.setUserId(userDetails.getId());
        jwtResponseDTO.setAccessToken(jwtToken);
        jwtResponseDTO.setExpiresIn(jwtService.getExpirationTime());

        return jwtResponseDTO;
    }
}
