package com.project.transferapi.domain.ports;

import com.project.transferapi.domain.entity.Token;

import java.util.Optional;

public interface FindTokenRepositoryPort {
    Optional<Token> findToken(String token);
}
