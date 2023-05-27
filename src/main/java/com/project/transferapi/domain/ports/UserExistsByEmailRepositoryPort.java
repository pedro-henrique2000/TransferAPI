package com.project.transferapi.domain.ports;

public interface UserExistsByEmailRepositoryPort {
   boolean existsByEmail(final String email);
}
