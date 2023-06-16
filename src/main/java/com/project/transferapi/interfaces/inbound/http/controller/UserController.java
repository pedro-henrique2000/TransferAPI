package com.project.transferapi.interfaces.inbound.http.controller;

import com.project.transferapi.application.CreateUser;
import com.project.transferapi.interfaces.inbound.http.dto.AuthenticationResponse;
import com.project.transferapi.interfaces.inbound.http.dto.CreateUserRequest;
import com.project.transferapi.interfaces.inbound.http.mapper.UserDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
@Tag(name = "User Management", description = "User Management Endpoints")
public class UserController {

    private final CreateUser createUser;
    private final UserDTOMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            description = "Register Endpoint",
            summary = "Register Endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success Response",
                            responseCode = "201"
                    )
            })
    public ResponseEntity<AuthenticationResponse> postUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        var authResponse = createUser.invoke(mapper.toUserEntity(createUserRequest));

        return ResponseEntity.status(201)
                .body(AuthenticationResponse.builder()
                        .accessToken(authResponse.getAccessToken())
                        .refreshToken(authResponse.getRefreshToken())
                        .build());
    }

}
