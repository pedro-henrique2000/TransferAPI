package com.project.transferapi.interfaces.outbound.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExternalTransactionNotifierServiceTest {

   @InjectMocks
   ExternalTransactionNotifierService transactionNotifierService;

   @Mock
   ExternalTransactionNotifierFeignClient externalTransactionNotifierFeignClient;

   @Test
   void shouldCallClient() {
      ResponseEntity responseEntity = mock(ResponseEntity.class);
      when(responseEntity.getStatusCode()).thenReturn(HttpStatusCode.valueOf(200));
      when(externalTransactionNotifierFeignClient.invoke()).thenReturn(responseEntity);

      this.transactionNotifierService.sendNotification("source", "dest", BigDecimal.TEN, "stat");
      verify(this.externalTransactionNotifierFeignClient, times(1)).invoke();
   }

}
