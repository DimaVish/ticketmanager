package com.smartticket.ticketmanager.dto;

import com.smartticket.ticketmanager.repository.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserDto {
    @NotNull
    private String userName;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String fullName;
    private String phone;
    @NotNull
    private Role role;
}