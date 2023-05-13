package com.project.transferapi.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class Transaction {

    private Long id;
    private BigDecimal amount;
    private User destination;
    private User source;
    private LocalDateTime createdAt;
    private TransactionStatus transactionStatus;

}
