package com.project.transferapi.application;

import com.project.transferapi.domain.entity.Authentication;
import com.project.transferapi.domain.exceptions.ResourceNotFoundException;
import com.project.transferapi.domain.exceptions.UnauthorizedException;
import com.project.transferapi.domain.ports.FindUserByEmailPort;
import com.project.transferapi.domain.ports.TokenHandlerPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshUserToken {

    private final RevokeUserTokens revokeUserTokens;
    private final SaveUserToken saveUserToken;
    private final TokenHandlerPort tokenHandler;
    private final FindUserByEmailPort findUserByEmail;

    public Authentication invoke(String refreshToken) {
        final String userEmail = tokenHandler.extractUsername(refreshToken);

        if (userEmail == null) {
            throw new UnauthorizedException("Received token");
        }

        var user = findUserByEmail.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with email " + userEmail));

        if (!tokenHandler.isTokenValid(refreshToken, user)) {
            throw new UnauthorizedException("Received invalid refresh token");
        }

        var accessToken = tokenHandler.generateToken(user);

        revokeUserTokens.invoke(user);

        saveUserToken.invoke(accessToken, user);

        return Authentication.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }
}

