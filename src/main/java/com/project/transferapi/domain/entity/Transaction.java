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
}
