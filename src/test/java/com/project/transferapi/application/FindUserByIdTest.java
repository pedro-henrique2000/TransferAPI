package com.project.transferapi.application;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.ResourceNotFoundException;
import com.project.transferapi.domain.ports.FindUserByIdPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindUserByIdTest {

   @InjectMocks
   FindUserById findUserById;

   @Mock
   FindUserByIdPort findUserByIdPort;

   @Test
   void shouldReturnUser() {
      User user = mock(User.class);
      when(this.findUserByIdPort.findUserById(anyLong())).thenReturn(Optional.of(user));
      User response = this.findUserById.invoke(1L);
      Assertions.assertEquals(user, response);
   }

   @Test
   void shouldThrowResourceNotFoundWhenEmptyUser() {
      when(this.findUserByIdPort.findUserById(anyLong())).thenReturn(Optional.empty());
      Assertions.assertThrows(ResourceNotFoundException.class, () -> {
         this.findUserById.invoke(1L);
      });
   }

}
