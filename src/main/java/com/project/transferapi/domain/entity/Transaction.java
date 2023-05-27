package com.project.transferapi.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_transactions")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne
    private User destination;

    @ManyToOne
    private User source;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (!getId().equals(that.getId())) return false;
        if (!getAmount().equals(that.getAmount())) return false;
        if (!getDestination().equals(that.getDestination())) return false;
        if (!getSource().equals(that.getSource())) return false;
        if (!getCreatedAt().equals(that.getCreatedAt())) return false;
        return getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getAmount().hashCode();
        result = 31 * result + getDestination().hashCode();
        result = 31 * result + getSource().hashCode();
        result = 31 * result + getCreatedAt().hashCode();
        result = 31 * result + getStatus().hashCode();
        return result;
    }
}
