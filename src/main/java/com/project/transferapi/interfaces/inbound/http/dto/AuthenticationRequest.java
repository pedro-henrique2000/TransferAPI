package com.project.transferapi.interfaces.inbound.http.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}