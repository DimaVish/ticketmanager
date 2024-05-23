package com.smartticket.ticketmanager.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String fullName;
    private String email;
    private String password;
    private String phone;
}