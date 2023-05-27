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
        lenient().when(userRepository.existsByEmail("email")).thenReturn(true);
        lenient().when(userRepository.existsByLegalDocumentNumberAllIgnoreCase("number")).thenReturn(true);
        lenient().when(userRepository.existsByLegalDocumentNumberAllIgnoreCase("email")).thenReturn(true);
        lenient().when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        lenient().when(userRepository.findById(1L)).thenReturn(Optional.of(expectedUser));
        lenient().when(userRepository.findByEmail("email")).thenReturn(Optional.of(expectedUser));
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
    void whenVerifyIfUserExistsByEmail_givenEmail_thenReturnBoolean() {
        boolean res = this.repository.existsByEmail("email");
        assertTrue(res);
    }

    @Test
    void whenVerifyIfUserExistsByDocument_givenDocument_thenReturnBoolean() {
        boolean res = this.repository.existsByDocumentNumber("number");
        assertTrue(res);
    }

    @Test
    void whenFindByEmail_givenEmail_thenReturnUser() {
        Optional<User> byEmail = this.repository.findByEmail("email");
        assertTrue(byEmail.isPresent());
    }


}
