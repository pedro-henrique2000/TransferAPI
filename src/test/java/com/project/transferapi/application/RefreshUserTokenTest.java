package com.project.transferapi.application;

import com.project.transferapi.domain.entity.Authentication;
import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.ResourceNotFoundException;
import com.project.transferapi.domain.exceptions.UnauthorizedException;
import com.project.transferapi.domain.ports.FindUserByEmailPort;
import com.project.transferapi.domain.ports.TokenHandlerPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshUserTokenTest {

    @InjectMocks
    RefreshUserToken refreshUserToken;
    @Mock
    RevokeUserTokens revokeUserTokens;
    @Mock
    SaveUserToken saveUserToken;
    @Mock
    TokenHandlerPort tokenHandler;
    @Mock
    FindUserByEmailPort findUserByEmail;
    @Mock
    User user;

    @BeforeEach
    void setup() {
        lenient().when(tokenHandler.extractUsername("token")).thenReturn("any_mail@mail.com");
        lenient().when(findUserByEmail.findByEmail("any_mail@mail.com")).thenReturn(Optional.of(user));
        lenient().when(tokenHandler.isTokenValid("token", user)).thenReturn(true);
        lenient().when(tokenHandler.generateToken(user)).thenReturn("newToken");
    }

    @Test
    void givenValidRefreshToken_shouldReturnANewOne() {
        Authentication authentication = this.refreshUserToken.invoke("token");

        Assertions.assertEquals("token", authentication.getRefreshToken());
        Assertions.assertEquals("newToken", authentication.getAccessToken());

        verify(revokeUserTokens, times(1)).invoke(user);
        verify(saveUserToken, times(1)).invoke("newToken", user);
    }

    @Test
    void givenInvalidRefreshToken_shouldThrowUnauthorizedException() {
        when(tokenHandler.extractUsername("token")).thenReturn(null);

        Assertions.assertThrows(UnauthorizedException.class, () -> {
            this.refreshUserToken.invoke("token");
        });
    }

    @Test
    void givenInvalidUserEmail_shouldThrowResourceNotFoundException() {
        when(findUserByEmail.findByEmail("any_mail@mail.com")).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            this.refreshUserToken.invoke("token");
        });
    }

    @Test
    void givenInvalidToken_shouldThrowUnauthorizedException() {
        when(tokenHandler.isTokenValid("token", user)).thenReturn(false);

        Assertions.assertThrows(UnauthorizedException.class, () -> {
            this.refreshUserToken.invoke("token");
        });
    }


}
