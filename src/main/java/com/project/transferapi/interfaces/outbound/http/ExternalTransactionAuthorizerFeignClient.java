package com.project.transferapi.interfaces.outbound.http;

import com.project.transferapi.interfaces.outbound.dto.ExternalAuthorizerResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "", name = "transaction-authorizer")
public interface ExternalTransactionAuthorizerFeignClient {
    @GetMapping
    ResponseEntity<ExternalAuthorizerResponseDTO> invoke();
}
