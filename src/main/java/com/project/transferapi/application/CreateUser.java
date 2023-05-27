package com.project.transferapi.application;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.ConflictException;
import com.project.transferapi.domain.ports.EncryptPasswordPort;
import com.project.transferapi.domain.ports.SaveUserRepositoryPort;
import com.project.transferapi.domain.ports.UserExistsByEmailRepositoryPort;
import com.project.transferapi.domain.ports.UserExistsByLegalDocumentNumberRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateUser {

   private final EncryptPasswordPort encryptPassword;
   private final SaveUserRepositoryPort saveUserRepository;
   private final UserExistsByEmailRepositoryPort userExistsByEmailRepository;
   private final UserExistsByLegalDocumentNumberRepositoryPort userExistsByLegalDocumentNumberRepository;

   public Long invoke(final User user) {
      if (this.userExistsByLegalDocumentNumberRepository.existsByDocumentNumber(user.getLegalDocumentNumber())) {
         throw new ConflictException("given document already registered");
      }

      if (this.userExistsByEmailRepository.existsByEmail(user.getEmail())) {
         throw new ConflictException("given email already registered");
      }

      log.info("CreateUser::invoke - Received user with email {}", user.getEmail());

      user.updatePassword(this.encryptPassword.encrypt(user.getPassword()));

      final User savedUser = this.saveUserRepository.save(user);

      log.info("CreateUser::invoke - Created user with email {}. Saved Id: {}", savedUser.getEmail(), savedUser.getId());

      return savedUser.getId();
   }

}
