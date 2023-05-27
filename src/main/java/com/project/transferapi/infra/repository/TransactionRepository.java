package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.Transaction;
import com.project.transferapi.domain.ports.SaveTransactionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionRepository implements SaveTransactionPort {

   private final JpaTransactionRepository jpaTransactionRepository;

   @Override
   public Transaction save(Transaction transaction) {
      return this.jpaTransactionRepository.save(transaction);
   }
}
