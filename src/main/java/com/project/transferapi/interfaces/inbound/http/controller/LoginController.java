package com.project.transferapi.interfaces.inbound.http.controller;

import com.project.transferapi.application.AuthenticateUser;
import com.project.transferapi.interfaces.inbound.http.dto.AuthenticationRequest;
import com.project.transferapi.interfaces.inbound.http.dto.AuthenticationResponse;
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
public class LoginController {

    private final AuthenticateUser authenticateUser;

    @PostMapping()
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
