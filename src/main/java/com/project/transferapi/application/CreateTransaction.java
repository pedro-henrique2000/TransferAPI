package com.project.transferapi.application;

import com.project.transferapi.domain.entity.Transaction;
import com.project.transferapi.domain.entity.TransactionStatus;
import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.ports.ISaveTransaction;
import com.project.transferapi.domain.ports.ISaveUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreateTransaction {

    private final ISaveTransaction saveTransaction;
    private final ISaveUserRepository saveUserRepository;

    public Transaction invoke(User destination, User source, BigDecimal amount, TransactionStatus status) {
        Transaction savedTransaction = this.saveTransaction.save(Transaction.builder()
                .source(source)
                .destination(destination)
                .amount(amount)
                .status(status)
                .build());

        destination.addReceivedTransaction(savedTransaction);
        source.addSentTransaction(savedTransaction);

        this.saveUserRepository.save(destination);
        this.saveUserRepository.save(source);

        return savedTransaction;
    }

}
