package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @InjectMocks
    UserRepository repository;

    @Mock
    JpaUserRepository userRepository;

    @Mock
    User user;

    @Mock
    User expectedUser;

    @BeforeEach
    void setup() {
        lenient().when(userRepository.findByEmail("any_mail@mail.com")).thenReturn(Optional.of(expectedUser));
        lenient().when(userRepository.findByLegalDocumentNumber("any_cnpj")).thenReturn(Optional.of(expectedUser));
        lenient().when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        lenient().when(userRepository.findById(1L)).thenReturn(Optional.of(expectedUser));
    }

    @Test
    void whenFindUserById_givenValidData_thenReturnSavedUser() {
        Optional<User> userOptional = repository.findUserById(1L);
        assertTrue(userOptional.isPresent());
    }

    @Test
    void whenFindUserById_givenInvalidData_thenReturnSavedUser() {
        lenient().when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> userOptional = repository.findUserById(1L);
        assertTrue(userOptional.isEmpty());
    }

    @Test
    void whenSaveNewUser_givenValidData_thenReturnSavedUser() {
        User savedUser = repository.save(user);
        assertEquals(expectedUser, savedUser);
    }

    @Test
    void whenFindUserByEmail_givenValidEmail_thenReturnOptionalUser() {
        Optional<User> optionalUser = repository.findByEmail("any_mail@mail.com");
        assertTrue(optionalUser.isPresent());
        assertEquals(expectedUser, optionalUser.get());
    }

    @Test
    void whenFindUserByEmail_givenInvalidEmail_thenReturnOptionalEmpty() {
        when(userRepository.findByEmail("invalid_mail@mail.com")).thenReturn(Optional.empty());
        Optional<User> user = repository.findByEmail("invalid_mail@mail.com");
        assertTrue(user.isEmpty());
    }

    @Test
    void whenFindUserByEmail_givenValidLegalDocumentNumber_thenReturnOptionalUser() {
        Optional<User> optionalUser = repository.findByLegalDocumentNumber("any_cnpj");
        assertTrue(optionalUser.isPresent());
        assertEquals(expectedUser, optionalUser.get());
    }

    @Test
    void whenFindUserByLegalDocumentNumber_givenInvalidLegalDocumentNumber_thenReturnOptionalEmpty() {
        when(userRepository.findByLegalDocumentNumber("any_cnpj")).thenReturn(Optional.empty());
        Optional<User> user = repository.findByLegalDocumentNumber("any_cnpj");
        assertTrue(user.isEmpty());
    }

}
