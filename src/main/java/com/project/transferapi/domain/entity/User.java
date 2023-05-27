package com.project.transferapi.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String fullName;

    @Column(nullable = false, unique = true, length = 14)
    private String legalDocumentNumber;

    @Column(nullable = false, unique = true, length = 50)
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
    private Role role;

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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRole().getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
