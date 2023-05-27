package com.project.transferapi.application;

import com.project.transferapi.domain.exceptions.BadCredentialsException;
import com.project.transferapi.domain.exceptions.ResourceNotFoundException;
import com.project.transferapi.domain.ports.FindUserByEmailPort;
import com.project.transferapi.domain.ports.GenerateAccessTokenPort;
import com.project.transferapi.domain.ports.ManagerAuthenticationPort;
import com.project.transferapi.domain.ports.PasswordComparerPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticateUser {

    private final ManagerAuthenticationPort managerAuthenticationPort;
    private final FindUserByEmailPort findUserByEmail;
    private final GenerateAccessTokenPort generateAccessToken;
    private final PasswordComparerPort passwordComparer;

    public String invoke(String email, String password) {
        var user = this.findUserByEmail.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Not found user " + email));

        if (!this.passwordComparer.equals(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        this.managerAuthenticationPort.authentication(email, password);

        return this.generateAccessToken.generateToken(user, user.getId(), user.getRole().name());
    }
}
