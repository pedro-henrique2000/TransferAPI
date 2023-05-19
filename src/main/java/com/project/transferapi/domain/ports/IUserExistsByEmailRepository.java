package com.project.transferapi.domain.ports;

public interface IUserExistsByEmailRepository {
    boolean existsByEmail(final String email);
}
