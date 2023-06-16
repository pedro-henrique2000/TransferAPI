package com.project.transferapi.infra.security.config;

import com.project.transferapi.domain.ports.ManagerAuthenticationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ManagerAuthentication implements ManagerAuthenticationPort {

   private final AuthenticationManager authenticationManager;

   public void authentication(String email, String password) {
      authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                  email, password
            )
      );
   }

}
