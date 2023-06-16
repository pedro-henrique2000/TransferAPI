package com.project.transferapi.application;

import com.project.transferapi.domain.entity.Authentication;
import com.project.transferapi.domain.exceptions.BadCredentialsException;
import com.project.transferapi.domain.ports.FindUserByEmailPort;
import com.project.transferapi.domain.ports.ManagerAuthenticationPort;
import com.project.transferapi.domain.ports.PasswordComparerPort;
import com.project.transferapi.domain.ports.TokenHandlerPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticateUser {

   private final SaveUserToken saveUserToken;
   private final RevokeUserTokens revokeUserTokens;
   private final ManagerAuthenticationPort managerAuthenticationPort;
   private final FindUserByEmailPort findUserByEmail;
   private final TokenHandlerPort generateAccessToken;
   private final PasswordComparerPort passwordComparer;

   public Authentication invoke(String email, String password) {
      var user = findUserByEmail.findByEmail(email)
            .orElseThrow(() -> new BadCredentialsException("Not found user " + email));

      if (!passwordComparer.equals(password, user.getPassword())) {
         throw new BadCredentialsException("Invalid password");
      }

      managerAuthenticationPort.authentication(email, password);

      var accessToken = generateAccessToken.generateToken(user);

      var refreshToken = generateAccessToken.generateRefreshToken(user);

      revokeUserTokens.invoke(user);

      saveUserToken.invoke(accessToken, user);

      return Authentication.builder()
              .accessToken(accessToken)
              .refreshToken(refreshToken)
              .build();
   }
}
