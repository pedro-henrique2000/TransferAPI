package com.project.transferapi.application;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.BusinessException;
import com.project.transferapi.domain.exceptions.ResourceNotFoundException;
import com.project.transferapi.domain.ports.ExternalTransactionAuthorizerPort;
import com.project.transferapi.domain.ports.FindUserByIdPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.project.transferapi.domain.entity.TransactionStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferAmount {

    private final FindUserByIdPort findUserById;
    private final ExternalTransactionAuthorizerPort externalTransactionAuthorizer;
    private final CreateTransaction createTransaction;

    public void invoke(Long sourceId, Long destinationId, BigDecimal amount) {
        User sourceUser = this.findUserById.findUserById(sourceId)
                .orElseThrow(() -> new ResourceNotFoundException("not found user with id " + sourceId));

        User destinationUser = this.findUserById.findUserById(destinationId)
                .orElseThrow(() -> new ResourceNotFoundException("not found user with id " + destinationId));

        log.info("TransferAmount::invoke - Received transaction from user_id {} to {}", sourceId, destinationId);

        if (sourceUser.isShopper()) {
            log.warn("TransferAmount::invoke - User {} is a shopper and does not have permission to transfer.", sourceId);
            throw new BusinessException("Source User is a shopper and does not have permission to transfer");
        }

        boolean wasDecreased = sourceUser.decreaseBalance(amount);
        if (!wasDecreased) {
            log.warn("TransferAmount::invoke - Transaction from user {} to {} failed due to insufficient funds.", sourceId, destinationId);
            this.createTransaction.invoke(destinationUser, sourceUser, amount, INSUFFICIENT_FUNDS);
            throw new BusinessException("Insufficient funds");
        }

        boolean wasTransactionApproved = this.externalTransactionAuthorizer.invoke();
        if (!wasTransactionApproved) {
            log.warn("TransferAmount::invoke - Transaction from user {} to {} was not authorized by the external service.", sourceId, destinationId);
            this.createTransaction.invoke(destinationUser, sourceUser, amount, NOT_AUTHORIZED);
            throw new BusinessException("Transaction was not authorized by the external service");
        }

        destinationUser.increaseBalance(amount);

        this.createTransaction.invoke(destinationUser, sourceUser, amount, COMPLETED);
        log.info("TransferAmount::invoke - Completed transaction from {} to {}", sourceId, destinationId);
    }

}
