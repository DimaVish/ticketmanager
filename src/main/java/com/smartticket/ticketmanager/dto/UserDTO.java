package com.smartticket.ticketmanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @NotEmpty(message = "Name cannot be empty")
    private String fullName;

    @Email(message = "Email should be valid")
    @NotNull
    private String email;

    @Size(min = 6, message = "Password should have at least 6 characters")
    @NotNull
    private String password;

    private String phone;
}