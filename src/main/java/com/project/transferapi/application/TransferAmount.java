package com.project.transferapi.application;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.BusinessException;
import com.project.transferapi.domain.exceptions.ResourceNotFoundException;
import com.project.transferapi.domain.ports.IExternalTransactionAuthorizer;
import com.project.transferapi.domain.ports.IFindUserById;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.project.transferapi.domain.entity.TransactionStatus.*;

@Service
@RequiredArgsConstructor
public class TransferAmount {

    private final IFindUserById findUserById;
    private final IExternalTransactionAuthorizer externalTransactionAuthorizer;
    private final CreateTransaction createTransaction;

    public void invoke(Long sourceId, Long destinationId, BigDecimal amount) {
        User sourceUser = this.findUserById.findUserById(sourceId)
                .orElseThrow(() -> new ResourceNotFoundException("not found user with id " + sourceId));

        User destinationUser = this.findUserById.findUserById(destinationId)
                .orElseThrow(() -> new ResourceNotFoundException("not found user with id " + destinationId));

        if (sourceUser.isShopper()) {
            throw new BusinessException("");
        }

        boolean wasTransactionApproved = this.externalTransactionAuthorizer.invoke();
        if (!wasTransactionApproved) {
            this.createTransaction.invoke(destinationUser, sourceUser, amount, NOT_AUTHORIZED);
            throw new BusinessException("");
        }

        boolean wasDecreased = sourceUser.decreaseBalance(amount);
        if (!wasDecreased) {
            this.createTransaction.invoke(destinationUser, sourceUser, amount, INSUFFICIENT_FUNDS);
            throw new BusinessException("");
        }

        destinationUser.increaseBalance(amount);

        this.createTransaction.invoke(destinationUser, sourceUser, amount, COMPLETED);
    }

}
