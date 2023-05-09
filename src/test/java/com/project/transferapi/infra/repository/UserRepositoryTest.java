package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.infra.mapper.UserModelMapper;
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

    @Test
    void whenFindUserByEmail_givenInvalidEmail_thenOptionalEmpty() {
        when(userRepository.findByEmail("invalid_mail@mail.com")).thenReturn(Optional.empty());
        Optional<User> user = repository.find("invalid_mail@mail.com");
        assertTrue(user.isEmpty());
    }

}
