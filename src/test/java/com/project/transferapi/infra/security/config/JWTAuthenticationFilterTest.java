package com.project.transferapi.infra.security.config;

import com.project.transferapi.domain.entity.Token;
import com.project.transferapi.domain.ports.FindTokenRepositoryPort;
import com.project.transferapi.domain.ports.TokenHandlerPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JWTAuthenticationFilterTest {

    @InjectMocks
    JWTAuthenticationFilter filter;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain chain;

    @Mock
    FindTokenRepositoryPort findToken;

    @Mock
    TokenHandlerPort jwtService;

    @Mock
    UserDetailsService userDetailsService;

    @Mock
    UserDetails user;

    @Mock
    Token token;

    @BeforeEach
    void setup() {
        lenient().when(request.getHeader("Authorization")).thenReturn("Bearer token");
        lenient().when(jwtService.extractUsername("token")).thenReturn("any_mail@mail.com");
        lenient().when(userDetailsService.loadUserByUsername("any_mail@mail.com")).thenReturn(user);
        lenient().when(findToken.findToken("token")).thenReturn(Optional.of(token));
        lenient().when(jwtService.isTokenValid("token", user)).thenReturn(true);
    }

    @Test
    void shouldCallDoFilterWhenSuccessAuthentication() {
        try {
            filter.doFilterInternal(request, response, chain);

            verify(jwtService, times(1)).isTokenValid("token", user);
            verify(chain, times(1)).doFilter(request, response);
        } catch (Exception exception) {
            fail(exception);
        }
    }

    @Test
    void shouldNotThrowWhenTokenIsExpired() {
        try {
            filter.doFilterInternal(request, response, chain);

            verify(jwtService, times(0)).isTokenValid("token", user);
            verify(chain, times(1)).doFilter(request, response);
        } catch (Exception exception) {
            fail(exception);
        }
    }

    @Test
    void shouldCallFilterWhenNullAuthHeader() {
        when(request.getHeader("Authorization")).thenReturn(null);

        try {
            filter.doFilterInternal(request, response, chain);

            verify(jwtService, times(0)).isTokenValid(any(), any());
            verify(chain, times(1)).doFilter(request, response);
        } catch (Exception exception) {
            fail(exception);
        }
    }

    @Test
    void shouldNotThrowWhenTokenIsRevoked() {
        try {
            filter.doFilterInternal(request, response, chain);

            verify(jwtService, times(0)).isTokenValid("token", user);
            verify(chain, times(1)).doFilter(request, response);
        } catch (Exception exception) {
            fail(exception);
        }
    }

}
