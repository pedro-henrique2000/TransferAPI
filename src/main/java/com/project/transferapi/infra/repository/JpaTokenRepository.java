package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface JpaTokenRepository extends JpaRepository<Token, Integer> {
    @Query("select t from Token t where t.user.id = ?1 and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokensByUser(Long id);

    Optional<Token> findByToken(String token);
}
