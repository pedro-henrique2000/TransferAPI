package com.project.transferapi.interfaces.inbound.http.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransferAmountDTOTest {

    @Test
    void shouldReturnFalseIfSourceAndDestinationAreEqual() {
        TransferAmountDTO transferAmountDTO = new TransferAmountDTO();
        transferAmountDTO.setSourceId(1L);
        transferAmountDTO.setDestinationId(1L);

        assertFalse(transferAmountDTO.isSourceAndDestinationNotEqual());
    }

    @Test
    void shouldReturnTrueIfSourceAndDestinationAreNotEqual() {
        TransferAmountDTO transferAmountDTO = new TransferAmountDTO();
        transferAmountDTO.setSourceId(1L);
        transferAmountDTO.setDestinationId(2L);

        assertTrue(transferAmountDTO.isSourceAndDestinationNotEqual());
    }


}
