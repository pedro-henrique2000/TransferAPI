package com.project.transferapi.application;

import com.project.transferapi.domain.entity.Token;
import com.project.transferapi.domain.entity.TokenType;
import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.ports.SaveTokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class SaveUserToken {

    private final SaveTokenRepositoryPort repository;

    public void invoke(final String token, final User user) {
        log.info("SaveUserToken:: saving token for user {}", user.getEmail());
        repository.save(buildToken(token, user));
    }

    private Token buildToken(String token, User user) {
        return Token.builder()
                .token(token)
                .expired(false)
                .revoked(false)
                .user(user)
                .tokenType(TokenType.BEARER)
                .build();
    }

}
