package com.project.transferapi.interfaces.outbound.http;

import com.project.transferapi.interfaces.outbound.dto.ExternalAuthorizerResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", name = "transaction-authorizer")
public interface ExternalTransactionAuthorizerFeignClient {
    @GetMapping
    ResponseEntity<ExternalAuthorizerResponseDTO> invoke();
}
