package com.project.transferapi.domain.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

   @Test
   void shouldReturnTrueIfUserIsTypeShopper() {
      User user = User.builder().type(UserType.SHOPPER).build();
      boolean decreased = user.isShopper();
      assertTrue(decreased);
   }

   @Test
   void shouldReturnFalseIfUserIsTypeCommon() {
      User user = User.builder().type(UserType.COMMON).build();
      boolean decreased = user.isShopper();
      assertFalse(decreased);
   }

   @Test
   void shouldAddReceivedTransaction() {
      Transaction transaction = Transaction.builder().build();
      User user = User.builder().build();
      user.addReceivedTransaction(transaction);
      assertEquals(1, user.getReceivedTransactions().size());
   }

   @Test
   void shouldAddSentTransaction() {
      Transaction transaction = Transaction.builder().build();
      User user = User.builder().build();
      user.addSentTransaction(transaction);
      assertEquals(1, user.getSentTransactions().size());
   }

   @Test
   void shouldReturnExpectedValues() {
      User user = User.builder()
            .id(1L)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

      assertEquals(1L, user.getId());
      assertNotNull(user.getCreatedAt());
      assertNotNull(user.getUpdatedAt());
      assertTrue(new User() instanceof User);
   }

   @Test
   void shouldReturnExpectedBooleanValues() {
      User user = User.builder()
            .build();

      assertTrue(user.isAccountNonLocked());
      assertTrue(user.isCredentialsNonExpired());
      assertTrue(user.isEnabled());
      assertTrue(user.isAccountNonExpired());
   }

   @Test
   void shouldReturnUsername() {
      User user = User.builder()
            .email("mail")
            .build();

      assertEquals("mail", user.getUsername());
   }

   @Test
   void shouldReturnAuthorities() {
      User user = User.builder()
            .role(Role.ADMIN)
            .build();

      var authorities = user.getAuthorities();
      assertFalse(authorities.isEmpty());
   }


}
