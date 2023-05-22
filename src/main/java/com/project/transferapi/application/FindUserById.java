package com.project.transferapi.application;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.ResourceNotFoundException;
import com.project.transferapi.domain.ports.FindUserByIdPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserById {

    private final FindUserByIdPort findUserByIdPort;

    public User invoke(final Long id) {
        return this.findUserByIdPort.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with id " + id));
    }

}
