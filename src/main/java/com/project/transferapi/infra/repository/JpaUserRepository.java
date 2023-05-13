package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);
    @Query("select u from User u where u.legalDocumentNumber = ?1")
    Optional<User> findByLegalDocumentNumber(String legalDocumentNumber);

}
