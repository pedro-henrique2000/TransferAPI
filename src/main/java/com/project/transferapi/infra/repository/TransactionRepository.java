package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.Transaction;
import com.project.transferapi.domain.ports.ISaveTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionRepository implements ISaveTransaction {

    private final JpaTransactionRepository jpaTransactionRepository;

    @Override
    public Transaction save(Transaction transaction) {
        return this.jpaTransactionRepository.save(transaction);
    }
}
