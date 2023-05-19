package com.project.transferapi.domain.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldIncreaseAmount() {
        User user = User.builder().balance(BigDecimal.ZERO).build();
        user.increaseBalance(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, user.getBalance());
    }

    @Test
    void shouldReturnFalseIfNotEnoughBalanceToDecrease() {
        User user = User.builder().balance(BigDecimal.ZERO).build();
        boolean decreased = user.decreaseBalance(BigDecimal.TEN);
        assertFalse(decreased);
    }

    @Test
    void shouldReturnTrueIfEnoughBalance() {
        User user = User.builder().balance(BigDecimal.TEN).build();
        boolean decreased = user.decreaseBalance(BigDecimal.ONE);
        assertTrue(decreased);
    }

    @Test
    void shouldReturnTrueIfEqualBalanceAndAmount() {
        User user = User.builder().balance(BigDecimal.TEN).build();
        boolean decreased = user.decreaseBalance(BigDecimal.TEN);
        assertTrue(decreased);
    }

}
