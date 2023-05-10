package com.project.transferapi.interfaces.inbound.http.controller;

import com.project.transferapi.application.TransferAmount;
import com.project.transferapi.interfaces.inbound.http.dto.TransferAmountDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferControllerTest {

    @InjectMocks
    TransferController transferController;

    @Mock
    TransferAmount transferAmount;

    @Test
    void shouldCallTransferAmount() {
        TransferAmountDTO transferAmountDTO = new TransferAmountDTO();
        transferAmountDTO.setAmount(BigDecimal.TEN);
        transferAmountDTO.setSourceId(1L);
        transferAmountDTO.setDestinationId(2L);

        ResponseEntity<Void> response = transferController.transfer(transferAmountDTO);

        verify(this.transferAmount, times(1)).invoke(1L, 2L, BigDecimal.TEN);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}
