package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.Token;
import com.project.transferapi.domain.ports.FindAllUserTokensRepositoryPort;
import com.project.transferapi.domain.ports.FindTokenRepositoryPort;
import com.project.transferapi.domain.ports.SaveTokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class TokenRepository implements SaveTokenRepositoryPort, FindAllUserTokensRepositoryPort, FindTokenRepositoryPort {

    private final JpaTokenRepository repository;

    @Override
    public Token save(Token token) {
        return repository.save(token);
    }

    @Override
    public List<Token> findAll(Long userid) {
        return repository.findAllValidTokensByUser(userid);
    }

    @Override
    public Optional<Token> findToken(String token) {
        return repository.findByToken(token);
    }
}
