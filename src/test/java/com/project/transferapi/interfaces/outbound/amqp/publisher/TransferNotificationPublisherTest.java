package com.project.transferapi.interfaces.outbound.amqp.publisher;

import com.project.transferapi.domain.entity.Transaction;
import com.project.transferapi.domain.entity.TransactionStatus;
import com.project.transferapi.domain.entity.User;
import com.project.transferapi.interfaces.outbound.dto.TransferNotificationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferNotificationPublisherTest {

   @InjectMocks
   TransferNotificationPublisher transferNotificationPublisher;

   @Mock
   RabbitTemplate rabbitTemplate;

   @Mock
   Transaction transaction;

   @BeforeEach()
   void setup() {
      ReflectionTestUtils.setField(this.transferNotificationPublisher, "exchange", "any_exchange");
      when(transaction.getStatus()).thenReturn(TransactionStatus.COMPLETED);
      when(transaction.getDestination()).thenReturn(mock(User.class));
      when(transaction.getSource()).thenReturn(mock(User.class));
   }

   @Test
   void shouldCallRabbitTemplateSend() {
      this.transferNotificationPublisher.notify(transaction);
      verify(rabbitTemplate, times(1)).convertAndSend(eq("any_exchange"), eq(""), any(TransferNotificationDTO.class));
   }

}
