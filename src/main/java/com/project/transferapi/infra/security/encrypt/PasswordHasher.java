package com.project.transferapi.infra.security.encrypt;

import com.project.transferapi.domain.ports.EncryptPasswordPort;
import com.project.transferapi.domain.ports.PasswordComparerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordHasher implements EncryptPasswordPort, PasswordComparerPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encrypt(final String password) {
        return this.passwordEncoder.encode(password);
    }

    @Override
    public boolean equals(String password, String encrypted) {
        return this.passwordEncoder.matches(password, encrypted);
    }

}
