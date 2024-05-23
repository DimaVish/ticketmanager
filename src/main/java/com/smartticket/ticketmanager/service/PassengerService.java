package com.smartticket.ticketmanager.service;

import com.smartticket.ticketmanager.dto.RegisterUserDto;
import com.smartticket.ticketmanager.dto.UserDTO;
import com.smartticket.ticketmanager.mappper.TicketManagerMapper;
import com.smartticket.ticketmanager.repository.UserRepository;
import com.smartticket.ticketmanager.repository.entities.Role;
import com.smartticket.ticketmanager.repository.entities.User;
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
    private final TicketManagerMapper mapper;

    // Create a new passenger user
    public UserDTO createPassenger(@Valid RegisterUserDto userDTO) {
        return mapper.toUserDTO(userService.createUser(userDTO, Role.ROLE_PASSENGER));
    }

    // Get all passengers
    public List<UserDTO> getAllPassengers() {
        return mapper.toUserDTOList(userRepository.findByRole(Role.ROLE_PASSENGER));
    }

    // Get a specific passenger by ID
    public UserDTO getPassengerById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return mapper.toUserDTO(user);
    }
}
