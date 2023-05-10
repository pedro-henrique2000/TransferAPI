package com.project.transferapi.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class User {

    private Long id;
    private String fullName;
    private String legalDocumentNumber;
    private String email;
    private String password;
    private BigDecimal balance;
    private UserType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void updatePassword(String password) {
        this.password = password;
    }

    public boolean isShopper() {
        return this.type.equals(UserType.SHOPPER);
    }

    public boolean decreaseBalance(BigDecimal amount) {
        boolean hasDecreased = false;
        if (amount.compareTo(this.balance) < 0) {
            this.balance = this.balance.subtract(amount);
            hasDecreased = true;
        }
        return hasDecreased;
    }

    public void increaseBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

}
