package com.project.transferapi.domain.ports;

import java.math.BigDecimal;

public interface IExternalTransactionNotifier {
    void sendNotification(String sourceName, String destinationName, BigDecimal amount, String status);
}
