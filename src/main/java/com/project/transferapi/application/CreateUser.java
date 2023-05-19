package com.project.transferapi.application;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.ConflictException;
import com.project.transferapi.domain.ports.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateUser {

    private final IEncryptPassword encryptPassword;
    private final ISaveUserRepository saveUserRepository;
    private final IUserExistsByEmailRepository userExistsByEmailRepository;
    private final IUserExistsByLegalDocumentNumberRepository userExistsByLegalDocumentNumberRepository;

    public Long invoke(final User user) {
        if (this.userExistsByLegalDocumentNumberRepository.existsByDocumentNumber(user.getLegalDocumentNumber())) {
            throw new ConflictException("given document already registered");
        }

        if (this.userExistsByEmailRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("given email already registered");
        }

        user.updatePassword(this.encryptPassword.encrypt(user.getPassword()));

        final User savedUser = this.saveUserRepository.save(user);

        return savedUser.getId();
    }

}
