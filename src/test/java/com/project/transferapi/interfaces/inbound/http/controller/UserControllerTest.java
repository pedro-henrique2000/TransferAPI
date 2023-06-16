package com.project.transferapi.interfaces.inbound.http.controller;

import com.project.transferapi.application.CreateUser;
import com.project.transferapi.domain.entity.Authentication;
import com.project.transferapi.domain.entity.User;
import com.project.transferapi.interfaces.inbound.http.dto.AuthenticationResponse;
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
      when(createUser.invoke(user)).thenReturn(Authentication.builder()
                      .refreshToken("refresh")
                      .accessToken("access")
              .build());


      ResponseEntity<AuthenticationResponse> res = controller.postUser(createUserRequest);


      assertEquals("refresh", res.getBody().getRefreshToken());
      assertEquals("access", res.getBody().getAccessToken());
      verify(this.createUser, times(1)).invoke(user);
   }

   @Test
   void whenPostNewUser_givenValidData_shouldReturnStatus201() {
      var authentication = Authentication.builder()
              .accessToken("access")
              .refreshToken("refresh")
              .build();

      when(createUser.invoke(any())).thenReturn(authentication);

      ResponseEntity<AuthenticationResponse> response = controller.postUser(createUserRequest);
      assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
      assertEquals("access", response.getBody().getAccessToken());
      assertEquals("refresh", response.getBody().getRefreshToken());
   }
}
