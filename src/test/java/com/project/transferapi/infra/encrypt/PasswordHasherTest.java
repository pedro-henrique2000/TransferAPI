package com.project.transferapi.infra.encrypt;

import com.project.transferapi.infra.security.encrypt.PasswordHasher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordHasherTest {

    @InjectMocks
    PasswordHasher passwordHasher;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void whenEncryptPassword_givenValidPassword_shouldReturnPassword() {
        when(this.passwordEncoder.encode("any_password")).thenReturn("new_password");
        String encrypted = this.passwordHasher.encrypt("any_password");
        assertEquals("new_password", encrypted);
    }

    @Test
    void whenEncryptPassword_givenValidPassword_shouldCallPasswordEncoder() {
        this.passwordHasher.encrypt("any_password");
        verify(this.passwordEncoder, times(1)).encode("any_password");
    }

    @Test
    void whenCompare_givenValidPassword_shouldReturnTrue() {
        when(this.passwordEncoder.matches("any_password", "hashed")).thenReturn(true);
        boolean equals = this.passwordHasher.equals("any_password", "hashed");
        assertTrue(equals);
    }

}
