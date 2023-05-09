package com.project.transferapi.application;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.ConflictException;
import com.project.transferapi.domain.ports.IEncryptPassword;
import com.project.transferapi.domain.ports.IFindUserByEmail;
import com.project.transferapi.domain.ports.IFindUserByLegalDocumentNumber;
import com.project.transferapi.domain.ports.ISaveUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateUser {

    private final IEncryptPassword encryptPassword;
    private final IFindUserByEmail findUserByEmail;
    private final IFindUserByLegalDocumentNumber findUserByLegalDocumentNumber;
    private final ISaveUserRepository saveUserRepository;

    public Long invoke(final User user) {
        this.findUserByLegalDocumentNumber.findByLegalDocumentNumber(user.getLegalDocumentNumber()).ifPresent(u -> {
            throw new ConflictException("given legal document number already registered");
        });

        this.findUserByEmail.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new ConflictException("given email already registered");
        });

        user.updatePassword(this.encryptPassword.encrypt(user.getPassword()));

        User savedUser = this.saveUserRepository.save(user);

        return savedUser.getId();
    }

}
