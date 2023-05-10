package com.project.transferapi.interfaces.outbound.http;

import com.project.transferapi.interfaces.outbound.dto.ExternalAuthorizerResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ExternalTransactionAuthorizerFeignClient {
    ResponseEntity<ExternalAuthorizerResponseDTO> invoke();
}
