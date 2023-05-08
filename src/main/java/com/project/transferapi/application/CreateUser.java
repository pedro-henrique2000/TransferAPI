package com.project.transferapi.application;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.ConflictException;
import com.project.transferapi.domain.ports.IFindUserByLegalDocumentNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateUser {

    private final IFindUserByLegalDocumentNumber findUserByLegalDocumentNumber;

    public Long invoke(final User user) {
        this.findUserByLegalDocumentNumber.find(user.getLegalDocumentNumber()).ifPresent(s -> {
            throw new ConflictException("given cnpj already registered");
        });

        return null;
    }

}
