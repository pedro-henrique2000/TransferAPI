package com.project.transferapi.interfaces.inbound.http.controller;

import com.project.transferapi.application.AuthenticateUser;
import com.project.transferapi.interfaces.inbound.http.dto.AuthenticationRequest;
import com.project.transferapi.interfaces.inbound.http.dto.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Tag(name = "User Management", description = "User Management Endpoints")
public class LoginController {

    private final AuthenticateUser authenticateUser;

    @PostMapping()
    @Operation(
            description = "Authenticate Endpoint",
            summary = "Authenticate Endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success Response",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))
                    )
            })
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        String token = this.authenticateUser.invoke(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
        );

        return ResponseEntity.ok(
                AuthenticationResponse.builder()
                        .accessToken(token)
                        .build());
    }

}
