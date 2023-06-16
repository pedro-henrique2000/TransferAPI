package com.project.transferapi.domain.ports;

import com.project.transferapi.domain.entity.Token;

public interface SaveTokenRepositoryPort {
    Token save(Token token);
}
