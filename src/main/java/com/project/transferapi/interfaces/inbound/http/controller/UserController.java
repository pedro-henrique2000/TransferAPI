package com.project.transferapi.interfaces.inbound.http.controller;

import com.project.transferapi.application.CreateUser;
import com.project.transferapi.interfaces.inbound.http.controller.api.UserAPI;
import com.project.transferapi.interfaces.inbound.http.dto.CreateUserDTO;
import com.project.transferapi.interfaces.inbound.http.mapper.UserDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class UserController implements UserAPI {

    private final CreateUser createUser;
    private final UserDTOMapper mapper;

    @Override
    public ResponseEntity<Void> postUser(CreateUserDTO createUserDTO) {
        return null;
    }

}
