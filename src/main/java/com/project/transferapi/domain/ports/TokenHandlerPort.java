package com.project.transferapi.domain.ports;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenHandlerPort {
   String generateToken(UserDetails user);
   String generateRefreshToken(UserDetails user);
   boolean isTokenValid(String jwt, UserDetails details);
   String extractUsername(String jwt);
}
