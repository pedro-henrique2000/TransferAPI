package com.project.transferapi.infra.security.config;

import com.project.transferapi.domain.ports.FindTokenRepositoryPort;
import com.project.transferapi.domain.ports.SaveTokenRepositoryPort;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final FindTokenRepositoryPort findTokenRepositoryPort;
    private final SaveTokenRepositoryPort saveTokenRepositoryPort;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return;

        jwt = authHeader.substring(7);
        var storedToken = findTokenRepositoryPort.findToken(jwt).orElse(null);
        if (Objects.nonNull(storedToken)) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            saveTokenRepositoryPort.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
