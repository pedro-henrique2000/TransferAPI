package com.project.transferapi.interfaces.inbound.http.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransferAmountRequestTest {

    @Test
    void shouldReturnFalseIfSourceAndDestinationAreEqual() {
        TransferAmountRequest transferAmountRequest = new TransferAmountRequest();
        transferAmountRequest.setSourceId(1L);
        transferAmountRequest.setDestinationId(1L);

        assertFalse(transferAmountRequest.isSourceAndDestinationNotEqual());
    }

    @Test
    void shouldReturnTrueIfSourceAndDestinationAreNotEqual() {
        TransferAmountRequest transferAmountRequest = new TransferAmountRequest();
        transferAmountRequest.setSourceId(1L);
        transferAmountRequest.setDestinationId(2L);

        assertTrue(transferAmountRequest.isSourceAndDestinationNotEqual());
    }


}
