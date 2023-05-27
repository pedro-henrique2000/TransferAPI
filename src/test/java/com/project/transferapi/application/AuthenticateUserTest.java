package com.project.transferapi.application;

import com.project.transferapi.domain.entity.Role;
import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.BadCredentialsException;
import com.project.transferapi.domain.ports.FindUserByEmailPort;
import com.project.transferapi.domain.ports.GenerateAccessTokenPort;
import com.project.transferapi.domain.ports.ManagerAuthenticationPort;
import com.project.transferapi.domain.ports.PasswordComparerPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticateUserTest {

    @InjectMocks
    AuthenticateUser authenticateUser;
    @Mock
    ManagerAuthenticationPort managerAuthenticationPort;
    @Mock
    FindUserByEmailPort findUserByEmail;
    @Mock
    GenerateAccessTokenPort generateAccessToken;
    @Mock
    PasswordComparerPort passwordComparer;
    @Mock
    User user;

    @BeforeEach
    void setup() {
        lenient().when(this.user.getPassword()).thenReturn("hash");
        lenient().when(this.user.getId()).thenReturn(1L);
        lenient().when(this.user.getRole()).thenReturn(Role.USER);
        lenient().when(this.findUserByEmail.findByEmail("email")).thenReturn(Optional.of(user));
        lenient().when(this.passwordComparer.equals("password", "hash")).thenReturn(true);
        lenient().when(this.generateAccessToken.generateToken(user, 1L, "USER")).thenReturn("token");
    }

    @Test
    void shouldReturnAccessToken() {
        String token = this.authenticateUser.invoke("email", "password");
        assertEquals("token", token);
        verify(this.managerAuthenticationPort, times(1)).authentication("email", "password");
    }

    @Test
    void shouldReturnBadCredentialsWhenUserNotFound() {
        when(this.findUserByEmail.findByEmail("email")).thenReturn(Optional.empty());
        assertThrows(BadCredentialsException.class, () -> {
            this.authenticateUser.invoke("email", "password");
        });
    }

    @Test
    void shouldReturnBadCredentialsWhenInvalidPassword() {
        lenient().when(this.passwordComparer.equals("password", "hash")).thenReturn(false);
        assertThrows(BadCredentialsException.class, () -> {
            this.authenticateUser.invoke("email", "password");
        });
    }

}
