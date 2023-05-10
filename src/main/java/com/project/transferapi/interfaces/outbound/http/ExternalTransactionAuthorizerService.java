package com.project.transferapi.interfaces.outbound.http;

import com.project.transferapi.domain.ports.IExternalTransactionAuthorizer;
import com.project.transferapi.interfaces.outbound.dto.ExternalAuthorizerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Component
public class ExternalTransactionAuthorizerService implements IExternalTransactionAuthorizer {

    private static final String APPROVED_MESSAGE = "Autorizado";
    private final ExternalTransactionAuthorizerFeignClient externalTransactionAuthorizerFeignClient;

    @Override
    public boolean invoke() {
        ResponseEntity<ExternalAuthorizerResponseDTO> response = this.externalTransactionAuthorizerFeignClient.invoke();
        ExternalAuthorizerResponseDTO responseBody = response.getBody();

        return response.getStatusCode().value() == 200
                && nonNull(responseBody)
                && APPROVED_MESSAGE.equals(responseBody.getMessage());
    }

}
