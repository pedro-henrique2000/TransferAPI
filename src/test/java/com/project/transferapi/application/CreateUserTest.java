package com.project.transferapi.application;

import com.project.transferapi.domain.entity.Authentication;
import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.ConflictException;
import com.project.transferapi.domain.ports.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserTest {

   @InjectMocks
   CreateUser createUser;

   @Mock
   UserExistsByLegalDocumentNumberRepositoryPort userExistsByLegalDocumentNumberRepository;

   @Mock
   UserExistsByEmailRepositoryPort userExistsByEmailRepository;

   @Mock
   EncryptPasswordPort encryptPassword;

   @Mock
   SaveUserRepositoryPort saveUserRepository;

   @Mock
   User user;

   @Mock
   User savedUser;

   @Mock
   SaveUserToken saveUserToken;

   @Mock
   TokenHandlerPort generateAccessToken;

   @Captor
   ArgumentCaptor<User> userToSave;

   @BeforeEach
   void setup() {
      lenient().when(this.user.getLegalDocumentNumber()).thenReturn("any_document");
      lenient().when(this.user.getEmail()).thenReturn("any_mail@mail.com");
      lenient().when(this.user.getPassword()).thenReturn("any_password");
      lenient().when(this.savedUser.getId()).thenReturn(1L);

      lenient().when(this.userExistsByLegalDocumentNumberRepository.existsByDocumentNumber("any_document")).thenReturn(false);
      lenient().when(this.userExistsByEmailRepository.existsByEmail("any_mail@mail.com")).thenReturn(false);
      lenient().when(this.encryptPassword.encrypt("any_password")).thenReturn("encrypted_password");
      lenient().when(this.generateAccessToken.generateToken(savedUser)).thenReturn("token");
      lenient().when(this.generateAccessToken.generateRefreshToken(savedUser)).thenReturn("refresh");
      lenient().when(this.saveUserRepository.save(any(User.class))).thenReturn(savedUser);
   }

   @Test
   void whenSaveNewUser_givenValidData_thenReturnSavedId() {
      Authentication authentication = this.createUser.invoke(user);
      assertEquals("token", authentication.getAccessToken());
      assertEquals("refresh", authentication.getRefreshToken());
   }

   @Test
   void whenSaveNewUser_givenAlreadyRegisteredLegalDocumentNumber_thenThrowConflictException() {
      when(this.userExistsByLegalDocumentNumberRepository.existsByDocumentNumber("any_document")).thenReturn(true);
      assertThrows(ConflictException.class, () -> {
         this.createUser.invoke(user);
      });
   }

   @Test
   void whenSaveNewUser_givenAlreadyRegisteredEmail_thenThrowConflictException() {
      when(this.userExistsByEmailRepository.existsByEmail("any_mail@mail.com")).thenReturn(true);
      assertThrows(ConflictException.class, () -> {
         this.createUser.invoke(user);
      });
   }

   @Test
   void whenSaveNewUser_givenValidPassword_thenCallPasswordEncrypt() {
      this.createUser.invoke(user);
      verify(this.encryptPassword, atLeastOnce()).encrypt("any_password");
   }

   @Test
   void whenSaveNewUser_givenGeneratedToken_thenCallSaveTokenMethod() {
      this.createUser.invoke(user);
      verify(this.saveUserToken, atLeastOnce()).invoke( "token", savedUser);
   }

   @Test
   void whenSaveNewUser_givenValidPassword_thenUpdateUserWithEncryptedPassword() {
      this.createUser.invoke(user);
      verify(this.user, atLeastOnce()).updatePassword("encrypted_password");
   }

   @Test
   void whenSaveNewUser_givenValidData_thenSave() {
      this.createUser.invoke(User.builder()
            .password("any_password")
            .email("any_mail@mail.com")
            .legalDocumentNumber("any_document")
            .build());
      verify(this.saveUserRepository, atLeastOnce()).save(userToSave.capture());
      assertEquals("encrypted_password", userToSave.getValue().getPassword());
   }
}
