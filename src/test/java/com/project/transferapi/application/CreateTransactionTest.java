package com.project.transferapi.application;

import com.project.transferapi.domain.entity.Transaction;
import com.project.transferapi.domain.entity.TransactionStatus;
import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.ports.ISaveTransaction;
import com.project.transferapi.domain.ports.ISaveUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTransactionTest {

    @InjectMocks
    CreateTransaction createTransaction;

    @Mock
    ISaveTransaction saveTransaction;

    @Mock
    ISaveUserRepository saveUserRepository;

    @Mock
    User source;

    @Mock
    User destination;

    @Mock
    Transaction savedTransaction;

    @BeforeEach
    void setup() {
        lenient().when(this.saveTransaction.save(any(Transaction.class))).thenReturn(savedTransaction);
    }

    @Test
    void whenReceiveTransactionData_givenValidData_thenCallRepository() {
        Transaction response = this.createTransaction.invoke(destination, source, BigDecimal.TEN, TransactionStatus.COMPLETED);

        verify(this.saveTransaction, times(1)).save(any(Transaction.class));
        assertEquals(savedTransaction, response);
    }

    @Test
    void whenReceiveTransactionData_givenValidData_thenAddTransactionToSourceAndDestinationUser() {
        this.createTransaction.invoke(destination, source, BigDecimal.TEN, TransactionStatus.COMPLETED);

        verify(this.destination, times(1)).addReceivedTransaction(savedTransaction);
        verify(this.source, times(1)).addSentTransaction(savedTransaction);
    }

    @Test
    void whenReceiveTransactionData_givenValidData_thenUpdateUser() {
        this.createTransaction.invoke(destination, source, BigDecimal.TEN, TransactionStatus.COMPLETED);

        verify(this.saveUserRepository, times(1)).save(destination);
        verify(this.saveUserRepository, times(1)).save(source);
    }

}
