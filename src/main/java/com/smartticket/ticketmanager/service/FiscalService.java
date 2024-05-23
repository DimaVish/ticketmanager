package com.smartticket.ticketmanager.service;

import com.smartticket.ticketmanager.dto.RegisterUserDto;
import com.smartticket.ticketmanager.dto.UserDTO;
import com.smartticket.ticketmanager.mappper.TicketManagerMapper;
import com.smartticket.ticketmanager.repository.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FiscalService {

    private final UserService userService;
    private final TicketManagerMapper mapper;

    // Create a new fiscal user
    public UserDTO createFiscal(RegisterUserDto userDTO) {
        return mapper.toUserDTO(userService.createUser(userDTO, Role.ROLE_FISCAL));
    }

    // Get all fiscals
    public List<UserDTO> getAllFiscals() {
        return mapper.toUserDTOList(userService.findAllUsersByRole(Role.ROLE_FISCAL));
    }

    // Get a specific fiscal by ID
    public UserDTO getFiscalById(Long userId) {
        return mapper.toUserDTO(userService.findUserById(userId));
    }
}
