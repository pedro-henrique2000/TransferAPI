package com.project.transferapi.interfaces.inbound.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.transferapi.application.AuthenticateUser;
import com.project.transferapi.application.RefreshUserToken;
import com.project.transferapi.interfaces.inbound.http.dto.AuthenticationRequest;
import com.project.transferapi.interfaces.inbound.http.dto.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Tag(name = "User Management", description = "User Management Endpoints")
public class LoginController {

    private final AuthenticateUser authenticateUser;
    private final RefreshUserToken refreshUserToken;

    @PostMapping()
    @Operation(description = "Authenticate Endpoint", summary = "Authenticate Endpoint",
            responses = {
                    @ApiResponse(description = "Success Response", responseCode = "200", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class)))
            })
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        var auth = this.authenticateUser.invoke(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
        );

        return ResponseEntity.ok(
                AuthenticationResponse.builder()
                        .accessToken(auth.getAccessToken())
                        .refreshToken(auth.getRefreshToken())
                        .build());
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        var authentication = refreshUserToken.invoke(authHeader.substring(7));

        new ObjectMapper().writeValue(response.getOutputStream(), authentication);
    }

}
