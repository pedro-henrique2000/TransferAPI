package com.project.transferapi.application;

import com.project.transferapi.domain.ports.IFindUserById;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferAmountTest {

    @InjectMocks
    TransferAmount transferAmount;

    @Mock
    IFindUserById findUserById;

    @Test
    void whenTransferAmount_givenValidSourceId_thenCallRepository() {
        this.transferAmount.invoke(1L, 2L, BigDecimal.ZERO);

        verify(this.findUserById, times(1)).findUserById(1L);
    }

}
