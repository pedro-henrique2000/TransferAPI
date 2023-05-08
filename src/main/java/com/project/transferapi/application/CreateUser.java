package com.project.transferapi.application;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.ConflictException;
import com.project.transferapi.domain.ports.IFindUserByEmail;
import com.project.transferapi.domain.ports.IFindUserByLegalDocumentNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateUser {

    private final IFindUserByEmail findUserByEmail;
    private final IFindUserByLegalDocumentNumber findUserByLegalDocumentNumber;

    public Long invoke(final User user) {
        this.findUserByLegalDocumentNumber.find(user.getLegalDocumentNumber()).ifPresent(u -> {
            throw new ConflictException("given legal document number already registered");
        });

        this.findUserByEmail.find(user.getEmail()).ifPresent(u -> {
            throw new ConflictException("given email already registered");
        });

        return null;
    }

}
