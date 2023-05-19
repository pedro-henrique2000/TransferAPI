package com.project.transferapi.application;

import com.project.transferapi.domain.entity.Transaction;
import com.project.transferapi.domain.entity.TransactionStatus;
import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.ports.PublishTransferNotificationPort;
import com.project.transferapi.domain.ports.SaveTransactionPort;
import com.project.transferapi.domain.ports.SaveUserRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateTransaction {

    private final SaveTransactionPort saveTransaction;
    private final SaveUserRepositoryPort saveUserRepository;
    private final PublishTransferNotificationPort publishTransferNotification;

    public Transaction invoke(User destination, User source, BigDecimal amount, TransactionStatus status) {
        log.info("CreateTransaction::invoke - Creating transaction for source {} and destination {}. Transaction Status Status {}", source.getId(), destination.getId(), status);
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

        this.publishTransferNotification.notify(savedTransaction);

        log.info("CreateTransaction::invoke - Created transaction for source {} and destination {}. Saved Id {}", source.getId(), destination.getId(), savedTransaction.getId());

        return savedTransaction;
    }

}
