package com.project.transferapi.application;

import com.project.transferapi.domain.ports.ExternalTransactionNotifierPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
@Slf4j
public class TransferNotificator {

   private final ExternalTransactionNotifierPort externalTransactionNotifier;

   public void invoke(String sourceName, String destinationName, BigDecimal amount, String status) {
      this.externalTransactionNotifier.sendNotification(sourceName, destinationName, amount, status);
   }

}
