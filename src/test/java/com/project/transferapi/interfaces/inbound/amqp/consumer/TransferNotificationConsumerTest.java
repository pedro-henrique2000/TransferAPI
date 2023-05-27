package com.project.transferapi.interfaces.inbound.amqp.consumer;

import com.project.transferapi.application.TransferNotificator;
import com.project.transferapi.interfaces.outbound.dto.TransferNotificationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferNotificationConsumerTest {

   @InjectMocks
   TransferNotificationConsumer consumer;

   @Mock
   TransferNotificator transferNotificator;

   @Mock
   TransferNotificationDTO transferNotificationDTO;

   @BeforeEach
   void setup() {
      when(transferNotificationDTO.getStatus()).thenReturn("status");
      when(transferNotificationDTO.getAmount()).thenReturn(BigDecimal.TEN);
      when(transferNotificationDTO.getSourceName()).thenReturn("source");
      when(transferNotificationDTO.getDestinationName()).thenReturn("dest");
   }

   @Test
   void shouldCallTransferNotificator() {
      consumer.consume(transferNotificationDTO);
      verify(this.transferNotificator, times(1)).invoke("source", "dest", BigDecimal.TEN, "status");
   }

}
