package com.project.transferapi.infra.repository;

import com.project.transferapi.infra.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface JpaUserRepository extends JpaRepository<UserModel, Long> {
    @Query("select u from UserModel u where u.email = ?1")
    Optional<UserModel> findByEmail(String email);
    @Query("select u from UserModel u where u.legalDocumentNumber = ?1")
    Optional<UserModel> findByLegalDocumentNumber(String legalDocumentNumber);

}
