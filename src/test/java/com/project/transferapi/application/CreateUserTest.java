package com.project.transferapi.application;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.ConflictException;
import com.project.transferapi.domain.ports.IFindUserByLegalDocumentNumber;
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
class CreateUserTest {

    @InjectMocks
    CreateUser createUser;

    @Mock
    IFindUserByLegalDocumentNumber findUserByLegalDocumentNumber;

    @Mock
    User user;

    @BeforeEach
    void setup() {
        lenient().when(this.findUserByLegalDocumentNumber.find(anyString())).thenReturn(Optional.empty());
    }

    @Test
    void whenSaveNewUserGivenAlreadyRegisteredLegalDocumentNumber_thenThrowConflictException() {
        when(this.findUserByLegalDocumentNumber.find(anyString())).thenReturn(Optional.of(mock(User.class)));
        assertThrows(ConflictException.class, () -> {
            this.createUser.invoke(user);
        });
    }
}
