package com.project.transferapi.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_users", indexes = {
        @Index(columnList = "legalDocumentNumber"),
        @Index(columnList = "email")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String legalDocumentNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "destination", fetch = FetchType.LAZY)
    private List<Transaction> receivedTransactions;

    @OneToMany(mappedBy = "source", fetch = FetchType.LAZY)
    private List<Transaction> sentTransactions;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType type;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void updatePassword(String password) {
        this.password = password;
    }

    public boolean isShopper() {
        return this.type.equals(UserType.SHOPPER);
    }

    public boolean decreaseBalance(BigDecimal amount) {
        boolean hasDecreased = false;
        if (amount.compareTo(this.balance) <= 0) {
            this.balance = this.balance.subtract(amount);
            hasDecreased = true;
        }
        return hasDecreased;
    }

    public void addReceivedTransaction(Transaction transaction) {
        if (this.receivedTransactions == null) {
            this.receivedTransactions =  new ArrayList<>();
        }
        this.receivedTransactions.add(transaction);
    }

    public void addSentTransaction(Transaction transaction) {
        if (this.sentTransactions == null) {
            this.sentTransactions =  new ArrayList<>();
        }
        this.sentTransactions.add(transaction);
    }

    public void increaseBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!getId().equals(user.getId())) return false;
        if (!getFullName().equals(user.getFullName())) return false;
        if (!getLegalDocumentNumber().equals(user.getLegalDocumentNumber())) return false;
        if (!getEmail().equals(user.getEmail())) return false;
        if (!getPassword().equals(user.getPassword())) return false;
        if (!getReceivedTransactions().equals(user.getReceivedTransactions())) return false;
        if (!getSentTransactions().equals(user.getSentTransactions())) return false;
        if (!getBalance().equals(user.getBalance())) return false;
        if (getType() != user.getType()) return false;
        if (!getCreatedAt().equals(user.getCreatedAt())) return false;
        return getUpdatedAt().equals(user.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getFullName().hashCode();
        result = 31 * result + getLegalDocumentNumber().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getReceivedTransactions().hashCode();
        result = 31 * result + getSentTransactions().hashCode();
        result = 31 * result + getBalance().hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getCreatedAt().hashCode();
        result = 31 * result + getUpdatedAt().hashCode();
        return result;
    }
}
