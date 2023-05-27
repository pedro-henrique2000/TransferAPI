package com.project.transferapi.interfaces.inbound.http.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
    @Email
    @NotBlank
    @Schema(required = true, description = "User Email", example = "email@mail.com")
    private String email;
    @NotBlank
    @Schema(required = true, description = "User Password", example = "123456")
    private String password;
}
