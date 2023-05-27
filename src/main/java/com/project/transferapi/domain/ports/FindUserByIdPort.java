package com.project.transferapi.domain.ports;

import com.project.transferapi.domain.entity.User;

import java.util.Optional;

public interface FindUserByIdPort {
   Optional<User> findUserById(Long id);
}
