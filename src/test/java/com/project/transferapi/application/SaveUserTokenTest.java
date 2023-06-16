package com.project.transferapi.application;

import com.project.transferapi.domain.entity.Token;
import com.project.transferapi.domain.entity.TokenType;
import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.ports.SaveTokenRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SaveUserTokenTest {

    @InjectMocks
    SaveUserToken saveUserToken;

    @Mock
    SaveTokenRepositoryPort repositoryPort;

    @Mock
    User user;

    @Captor
    ArgumentCaptor<Token> token;

    @Test
    void shouldCallRepository() {
        saveUserToken.invoke("token", user);
        Mockito.verify(repositoryPort, Mockito.times(1)).save(token.capture());

        assertEquals(user, token.getValue().getUser());
        assertEquals("token", token.getValue().getToken());
        assertEquals(TokenType.BEARER, token.getValue().getTokenType());
        assertFalse(token.getValue().isExpired());
        assertFalse(token.getValue().isRevoked());
    }


}
