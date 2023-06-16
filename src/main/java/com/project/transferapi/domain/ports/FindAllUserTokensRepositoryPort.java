package com.project.transferapi.domain.ports;

import com.project.transferapi.domain.entity.Token;

import java.util.List;

public interface FindAllUserTokensRepositoryPort {
    List<Token> findAll(Long userid);
}
