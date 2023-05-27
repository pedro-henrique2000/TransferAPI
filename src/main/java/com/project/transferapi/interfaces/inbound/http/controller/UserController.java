package com.project.transferapi.interfaces.inbound.http.controller;

import com.project.transferapi.application.CreateUser;
import com.project.transferapi.interfaces.inbound.http.controller.api.UserAPI;
import com.project.transferapi.interfaces.inbound.http.dto.CreateUserDTO;
import com.project.transferapi.interfaces.inbound.http.mapper.UserDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController implements UserAPI {

    private final CreateUser createUser;
    private final UserDTOMapper mapper;

    @Override
    public ResponseEntity<Void> postUser(CreateUserDTO createUserDTO) {
        this.createUser.invoke(this.mapper.toUserEntity(createUserDTO));

        return ResponseEntity
                .status(201)
                .build();
    }

}
