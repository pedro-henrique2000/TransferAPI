package com.project.transferapi.interfaces.outbound.http;

import com.project.transferapi.interfaces.outbound.dto.ExternalAuthorizerResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExternalTransactionAuthorizerServiceTest {

    @InjectMocks
    ExternalTransactionAuthorizerService externalTransactionAuthorizerService;

    @Mock
    ExternalTransactionAuthorizerFeignClient externalTransactionAuthorizerFeignClient;

    @Mock
    ExternalAuthorizerResponseDTO externalAuthorizerResponseDTO;

    @Mock
    ResponseEntity<ExternalAuthorizerResponseDTO> responseEntity;

    @BeforeEach
    void setup() {
        lenient().when(this.externalAuthorizerResponseDTO.getMessage()).thenReturn("Autorizado");
        lenient().when(this.responseEntity.getStatusCode()).thenReturn(HttpStatusCode.valueOf(200));
        lenient().when(this.responseEntity.getBody()).thenReturn(externalAuthorizerResponseDTO);
        lenient().when(this.externalTransactionAuthorizerFeignClient.invoke()).thenReturn(responseEntity);
    }

    @Test
    void whenCallTransferAuthorizerService_givenSuccessResponse_thenReturnTrue() {
        boolean result = this.externalTransactionAuthorizerService.invoke();
        assertTrue(result);
    }

    @Test
    void whenCallTransferAuthorizerService_givenFailedResponse_thenReturnFalse() {
        when(this.responseEntity.getStatusCode()).thenReturn(HttpStatusCode.valueOf(500));
        boolean result = this.externalTransactionAuthorizerService.invoke();
        assertFalse(result);
    }

}
