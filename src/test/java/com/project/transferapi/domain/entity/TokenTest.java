package com.project.transferapi.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void shouldReturnExpectedValues() {
        var token = buildToken();

        assertEquals(1, token.getId());
        assertEquals(TokenType.BEARER, token.getTokenType());
        assertEquals("token", token.getToken());
        assertFalse(token.isExpired());
        assertFalse(token.isRevoked());
    }

    @Test
    void shouldSetExpiredAndRevokedToTrue() {
        var token = buildToken();

        token.setExpired(true);
        token.setRevoked(true);

        assertTrue(token.isExpired());
        assertTrue(token.isRevoked());
    }

    private Token buildToken() {
        return Token.builder()
                .id(1)
                .tokenType(TokenType.BEARER)
                .user(new User())
                .token("token")
                .revoked(false)
                .expired(false)
                .build();
    }

}
