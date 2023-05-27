package com.project.transferapi.interfaces.inbound.http.controller;

import com.project.transferapi.application.CreateUser;
import com.project.transferapi.domain.entity.User;
import com.project.transferapi.interfaces.inbound.http.dto.CreateUserRequest;
import com.project.transferapi.interfaces.inbound.http.mapper.UserDTOMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

   @InjectMocks
   UserController controller;

   @Mock
   CreateUser createUser;

   @Mock
   UserDTOMapper userDTOMapper;

   @Mock
   CreateUserRequest createUserRequest;

   @Mock
   User user;

   @Test
   void whenPostNewUser_givenValidData_shouldCallCreateUserMethod() {
      when(this.userDTOMapper.toUserEntity(createUserRequest)).thenReturn(user);
      controller.postUser(createUserRequest);

      verify(this.createUser, times(1)).invoke(user);
   }

   @Test
   void whenPostNewUser_givenValidData_shouldReturnStatus201() {
      ResponseEntity<Void> response = controller.postUser(createUserRequest);
      assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
   }
}
