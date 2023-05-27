package com.project.transferapi.domain.ports;

import org.springframework.security.core.userdetails.UserDetails;

public interface GenerateAccessTokenPort {
    String generateToken(UserDetails user, Long id, String role);
}
