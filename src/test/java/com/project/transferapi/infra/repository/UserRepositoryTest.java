package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.infra.mapper.UserModelMapper;
import com.project.transferapi.infra.model.UserModel;
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
    UserModelMapper userModelMapper;

    @Mock
    User user;

    @Mock
    UserModel userModel;

    @BeforeEach
    void setup() {
        lenient().when(userModelMapper.toEntity(userModel)).thenReturn(user);
        lenient().when(userRepository.findByEmail("any_mail@mail.com")).thenReturn(Optional.of(userModel));
        lenient().when(userRepository.findByLegalDocumentNumber("any_cnpj")).thenReturn(Optional.of(userModel));
    }

    @Test
    void whenFindUserByEmail_givenValidEmail_thenReturnOptionalUser() {
        Optional<User> optionalUser = repository.findByEmail("any_mail@mail.com");
        assertTrue(optionalUser.isPresent());
        assertEquals(user, optionalUser.get());
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
        assertEquals(user, optionalUser.get());
    }

    @Test
    void whenFindUserByLegalDocumentNumber_givenInvalidLegalDocumentNumber_thenReturnOptionalEmpty() {
        when(userRepository.findByLegalDocumentNumber("any_cnpj")).thenReturn(Optional.empty());
        Optional<User> user = repository.findByLegalDocumentNumber("any_cnpj");
        assertTrue(user.isEmpty());
    }

}
