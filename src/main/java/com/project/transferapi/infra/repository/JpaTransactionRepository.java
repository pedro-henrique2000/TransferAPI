package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTransactionRepository extends JpaRepository<Transaction, Long> {
}
