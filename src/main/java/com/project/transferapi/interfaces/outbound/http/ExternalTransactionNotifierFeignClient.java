package com.project.transferapi.interfaces.outbound.http;

import com.project.transferapi.interfaces.outbound.dto.ExternalNotificationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "http://o4d9z.mocklab.io/notify", name = "transaction-notifier")
public interface ExternalTransactionNotifierFeignClient {
    @GetMapping
    ResponseEntity<ExternalNotificationResponseDTO> invoke();
}
