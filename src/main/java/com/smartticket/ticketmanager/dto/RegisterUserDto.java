package com.smartticket.ticketmanager.dto;

import com.smartticket.ticketmanager.repository.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserDto {
    @NotEmpty(message = "userName cannot be empty")
    private String userName;
    @NotEmpty(message = "email cannot be empty")
    @Email
    private String email;
    @NotEmpty(message = "password cannot be empty")
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;
    @NotNull
    @NotEmpty(message = "fullName cannot be empty")
    private String fullName;
    private String phone;
    @NotNull
    private Role role;
}