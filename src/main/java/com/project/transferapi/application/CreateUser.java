package com.project.transferapi.application;

import com.project.transferapi.domain.entity.Authentication;
import com.project.transferapi.domain.entity.Token;
import com.project.transferapi.domain.entity.TokenType;
import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.ConflictException;
import com.project.transferapi.domain.ports.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateUser {

   private final SaveUserToken saveUserToken;
   private final TokenHandlerPort generateAccessToken;
   private final EncryptPasswordPort encryptPassword;
   private final SaveUserRepositoryPort saveUserRepository;
   private final UserExistsByEmailRepositoryPort userExistsByEmailRepository;
   private final UserExistsByLegalDocumentNumberRepositoryPort userExistsByLegalDocumentNumberRepository;

   public Authentication invoke(final User user) {
      if (userExistsByLegalDocumentNumberRepository.existsByDocumentNumber(user.getLegalDocumentNumber())) {
         throw new ConflictException("given document already registered");
      }

      if (userExistsByEmailRepository.existsByEmail(user.getEmail())) {
         throw new ConflictException("given email already registered");
      }

      log.info("CreateUser::invoke - Received user with email {}", user.getEmail());

      user.updatePassword(encryptPassword.encrypt(user.getPassword()));

      var savedUser = saveUserRepository.save(user);

      var accessToken = generateAccessToken.generateToken(savedUser);

      var refreshToken = generateAccessToken.generateRefreshToken(savedUser);

      saveUserToken.invoke(accessToken, savedUser);

      log.info("CreateUser::invoke - Created user with email {}. Saved Id: {}", savedUser.getEmail(), savedUser.getId());

      return Authentication.builder()
              .accessToken(accessToken)
              .refreshToken(refreshToken)
              .build();
   }

}
