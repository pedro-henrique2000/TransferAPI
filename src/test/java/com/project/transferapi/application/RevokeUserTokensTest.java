package com.project.transferapi.application;

import com.project.transferapi.domain.entity.Token;
import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.ports.FindAllUserTokensRepositoryPort;
import com.project.transferapi.domain.ports.SaveTokenRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RevokeUserTokensTest {


    @InjectMocks
    RevokeUserTokens revokeUserTokens;

    @Mock
    FindAllUserTokensRepositoryPort findAllUserTokensRepository;

    @Mock
    SaveTokenRepositoryPort saveTokenRepositoryPort;

    @Mock
    User user;

    @Mock
    Token firstToken;

    @Mock
    Token secondToken;

    @BeforeEach
    void setup() {
        lenient().when(user.getId()).thenReturn(1L);
        lenient().when(findAllUserTokensRepository.findAll(1L)).thenReturn(List.of(firstToken, secondToken));
    }

    @Test
    void shouldRevokeAllUserTokens() {
        revokeUserTokens.invoke(user);

        verify(firstToken, times(1)).setRevoked(true);
        verify(firstToken, times(1)).setExpired(true);

        verify(secondToken, times(1)).setRevoked(true);
        verify(secondToken, times(1)).setExpired(true);

        verify(saveTokenRepositoryPort, times(1)).save(firstToken);
        verify(saveTokenRepositoryPort, times(1)).save(secondToken);
    }

    @Test
    void shouldNotSaveWhenUserNotContainsTokens() {
        when(findAllUserTokensRepository.findAll(any())).thenReturn(Collections.emptyList());

        revokeUserTokens.invoke(user);

        verify(saveTokenRepositoryPort, times(0)).save(any());
    }

}
