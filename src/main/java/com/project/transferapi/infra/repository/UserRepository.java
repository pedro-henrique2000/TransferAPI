package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.ports.FindUserByIdPort;
import com.project.transferapi.domain.ports.SaveUserRepositoryPort;
import com.project.transferapi.domain.ports.UserExistsByEmailRepositoryPort;
import com.project.transferapi.domain.ports.UserExistsByLegalDocumentNumberRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepository implements SaveUserRepositoryPort, FindUserByIdPort, UserExistsByEmailRepositoryPort, UserExistsByLegalDocumentNumberRepositoryPort {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<User> findUserById(Long id) {
        return this.jpaUserRepository.findById(id);
    }

    @Override
    public User save(final User user) {
        return this.jpaUserRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.jpaUserRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByDocumentNumber(String number) {
        return this.jpaUserRepository.existsByLegalDocumentNumberAllIgnoreCase(number);
    }
}
