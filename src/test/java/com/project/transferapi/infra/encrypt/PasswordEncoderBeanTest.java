package com.project.transferapi.infra.encrypt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PasswordEncoderBeanTest {

    @InjectMocks
    PasswordEncoderBean passwordEncoderBean;

    @Test
    void whenGetInstance_shouldReturnExpectedInstance() {
        PasswordEncoder passwordEncoder = passwordEncoderBean.passwordEncoder();
        assertNotNull(passwordEncoder);
    }

}
