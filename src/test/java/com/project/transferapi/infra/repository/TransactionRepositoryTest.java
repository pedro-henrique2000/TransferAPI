package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionRepositoryTest {

   @InjectMocks
   TransactionRepository transactionRepository;

   @Mock
   JpaTransactionRepository jpaTransactionRepository;

   @Mock
   Transaction transactionToSave;

   @Mock
   Transaction savedTransaction;

   @Test
   void whenSaveTransaction_givenTransaction_thenCallSave() {
      when(jpaTransactionRepository.save(transactionToSave)).thenReturn(savedTransaction);

      Transaction res = this.transactionRepository.save(transactionToSave);

      assertEquals(savedTransaction, res);
   }

}
