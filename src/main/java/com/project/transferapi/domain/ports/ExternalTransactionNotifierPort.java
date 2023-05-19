package com.project.transferapi.domain.ports;

import java.math.BigDecimal;

public interface ExternalTransactionNotifierPort {
    void sendNotification(String sourceName, String destinationName, BigDecimal amount, String status);
}
