package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.ports.IFindUserByEmail;
import com.project.transferapi.domain.ports.IFindUserById;
import com.project.transferapi.domain.ports.IFindUserByLegalDocumentNumber;
import com.project.transferapi.domain.ports.ISaveUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepository implements IFindUserByEmail, IFindUserByLegalDocumentNumber, ISaveUserRepository, IFindUserById {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<User> findByEmail(final String email) {
        return this.jpaUserRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByLegalDocumentNumber(final String legalDocumentNumber) {
        return this.jpaUserRepository.findByLegalDocumentNumber(legalDocumentNumber);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return this.jpaUserRepository.findById(id);
    }

    @Override
    public User save(final User user) {
        return this.jpaUserRepository.save(user);
    }
}
