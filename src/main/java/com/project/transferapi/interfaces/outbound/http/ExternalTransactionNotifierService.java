package com.project.transferapi.interfaces.outbound.http;

import com.project.transferapi.domain.ports.IExternalTransactionNotifier;
import com.project.transferapi.interfaces.outbound.dto.ExternalNotificationResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Component
public class ExternalTransactionNotifierService implements IExternalTransactionNotifier {

    private final ExternalTransactionNotifierFeignClient externalTransactionNotifierFeignClient;

    @Override
    public void sendNotification(String sourceName, String destinationName, BigDecimal amount, String status) {
        log.info("ExternalTransactionNotifierService::invoke - Sending request to External notification service");
        log.debug("{} {} {} {}", sourceName, destinationName, amount, status);
        ResponseEntity<ExternalNotificationResponseDTO> response = this.externalTransactionNotifierFeignClient.invoke();
        log.info("ExternalTransactionNotifierService::invoke - Sent request to external notification service. Received Response {}", response.getStatusCode());
    }
}
