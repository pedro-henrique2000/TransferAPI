package com.project.transferapi.application;

import com.project.transferapi.domain.entity.Transaction;
import com.project.transferapi.domain.entity.TransactionStatus;
import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.BusinessException;
import com.project.transferapi.domain.ports.ExternalTransactionAuthorizerPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferAmountTest {

   @InjectMocks
   TransferAmount transferAmount;

   @Mock
   FindUserById findUserById;

   @Mock
   ExternalTransactionAuthorizerPort externalTransactionAuthorizer;

   @Mock
   CreateTransaction createTransaction;

   @Mock
   Transaction transaction;

   @Mock
   User sourceUser;

   @Mock
   User destinationUser;

   @BeforeEach
   void setup() {
      lenient().when(this.sourceUser.isShopper()).thenReturn(false);
      lenient().when(this.sourceUser.decreaseBalance(any(BigDecimal.class))).thenReturn(true);
      lenient().when(this.findUserById.invoke(1L)).thenReturn(sourceUser);
      lenient().when(this.findUserById.invoke(2L)).thenReturn(destinationUser);
      lenient().when(this.externalTransactionAuthorizer.invoke()).thenReturn(true);
      lenient().when(this.createTransaction.invoke(any(User.class), any(User.class), any(BigDecimal.class), any(TransactionStatus.class))).thenReturn(transaction);
   }

   @Test
   void whenTransferAmount_givenGivenValidData_thenCallTransferNotification() {
      this.transferAmount.invoke(1L, 2L, BigDecimal.ZERO);

      verify(createTransaction, times(1)).invoke(destinationUser, sourceUser, BigDecimal.ZERO, TransactionStatus.COMPLETED);
   }

   @Test
   void whenTransferAmount_givenGivenInvalidAmount_thenThrowBusinessException() {
      when(this.sourceUser.decreaseBalance(any(BigDecimal.class))).thenReturn(false);
      assertThrows(BusinessException.class, () -> {
         this.transferAmount.invoke(1L, 2L, BigDecimal.ZERO);
      });

      verify(createTransaction, times(1)).invoke(destinationUser, sourceUser, BigDecimal.ZERO, TransactionStatus.INSUFFICIENT_FUNDS);
   }

   @Test
   void whenTransferAmount_givenGivenValidAmount_thenCallSourceIncreaseQuantity() {
      this.transferAmount.invoke(1L, 2L, BigDecimal.ZERO);

      verify(this.destinationUser, times(1)).increaseBalance(BigDecimal.ZERO);
   }

   @Test
   void whenTransferAmount_givenNegatedTransaction_thenThrowBusinessException() {
      when(this.externalTransactionAuthorizer.invoke()).thenReturn(false);

      assertThrows(BusinessException.class, () -> {
         this.transferAmount.invoke(1L, 2L, BigDecimal.ZERO);
      });

      verify(createTransaction, times(1)).invoke(destinationUser, sourceUser, BigDecimal.ZERO, TransactionStatus.NOT_AUTHORIZED);

   }

   @Test
   void whenTransferAmount_givenValidData_thenCallExternalAuthorizerService() {
      this.transferAmount.invoke(1L, 2L, BigDecimal.ZERO);

      verify(externalTransactionAuthorizer, times(1)).invoke();
   }

   @Test
   void whenTransferAmount_givenSourceUserOfTypeShopper_thenThrowBusinessException() {
      when(sourceUser.isShopper()).thenReturn(true);
      assertThrows(BusinessException.class, () -> {
         this.transferAmount.invoke(1L, 2L, BigDecimal.ZERO);
      });
   }

}
