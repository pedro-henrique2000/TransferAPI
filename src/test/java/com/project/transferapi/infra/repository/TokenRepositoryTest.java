package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class TokenRepositoryTest {

    @InjectMocks
    TokenRepository repository;

    @Mock
    JpaTokenRepository jpaTokenRepository;

    @Mock
    Token token;

    @BeforeEach
    void setup() {
        lenient().when(jpaTokenRepository.findAllValidTokensByUser(1L)).thenReturn(List.of(token));
        lenient().when(jpaTokenRepository.findByToken("token")).thenReturn(Optional.of(token));
    }

    @Test
    void shouldCallSave() {
        repository.save(token);
        Mockito.verify(jpaTokenRepository, Mockito.times(1)).save(token);
    }

    @Test
    void shouldReturnAllUserTokens() {
        var tokens = repository.findAll(1L);
        assertEquals(token, tokens.get(0));
    }

    @Test
    void shouldReturnToken() {
        var response = repository.findToken("token");
        assertTrue(response.isPresent());
        assertEquals(token, response.get());
    }


}
