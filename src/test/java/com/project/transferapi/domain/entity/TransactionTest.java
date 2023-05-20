package com.project.transferapi.domain.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void shouldReturnExpectedValues() {
        User source = User.builder().build();
        User destination = User.builder().build();

        Transaction transaction = Transaction.builder()
                .status(TransactionStatus.COMPLETED)
                .amount(BigDecimal.TEN)
                .destination(destination)
                .source(source)
                .id(1L)
                .createdAt(LocalDateTime.now())
                .build();

        assertEquals(TransactionStatus.COMPLETED, transaction.getStatus());
        assertEquals(BigDecimal.TEN, transaction.getAmount());
        assertEquals(destination, transaction.getDestination());
        assertEquals(source, transaction.getSource());
        assertEquals(1L, transaction.getId());
        assertNotNull(transaction.getCreatedAt());
        assertTrue(new Transaction() instanceof Transaction);
    }

}
