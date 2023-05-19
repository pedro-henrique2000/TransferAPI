package com.project.transferapi.infra.encrypt;

import com.project.transferapi.domain.ports.EncryptPasswordPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncryptPassword implements EncryptPasswordPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encrypt(final String password) {
        return this.passwordEncoder.encode(password);
    }

}
