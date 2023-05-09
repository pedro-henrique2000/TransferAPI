package com.project.transferapi.interfaces.inbound.http.controller.api;

import com.project.transferapi.interfaces.inbound.http.dto.CreateUserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface UserAPI {

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> postUser(@RequestBody @Valid CreateUserDTO createUserDTO);

}
