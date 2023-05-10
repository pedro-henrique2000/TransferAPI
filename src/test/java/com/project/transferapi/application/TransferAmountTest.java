package com.project.transferapi.application;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.BusinessException;
import com.project.transferapi.domain.exceptions.ResourceNotFoundException;
import com.project.transferapi.domain.ports.IExternalTransactionAuthorizer;
import com.project.transferapi.domain.ports.IFindUserById;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferAmountTest {

    @InjectMocks
    TransferAmount transferAmount;

    @Mock
    IFindUserById findUserById;

    @Mock
    IExternalTransactionAuthorizer externalTransactionAuthorizer;

    @Mock
    User sourceUser;

    @Mock
    User destinationUser;

    @BeforeEach
    void setup() {
        lenient().when(this.sourceUser.isShopper()).thenReturn(false);
        lenient().when(this.findUserById.findUserById(1L)).thenReturn(Optional.of(sourceUser));
        lenient().when(this.findUserById.findUserById(2L)).thenReturn(Optional.of(destinationUser));
        lenient().when(this.externalTransactionAuthorizer.invoke()).thenReturn(true);
    }

    @Test
    void whenTransferAmount_givenNegatedTransaction_thenThrowBusinessException() {
        when(this.externalTransactionAuthorizer.invoke()).thenReturn(false);

        assertThrows(BusinessException.class, () -> {
            this.transferAmount.invoke(1L, 2L, BigDecimal.ZERO);
        });
    }

    @Test
    void whenTransferAmount_givenValidData_thenCallExternalAuthorizerService() {
        this.transferAmount.invoke(1L, 2L, BigDecimal.ZERO);

        verify(externalTransactionAuthorizer, times(1)).invoke();
    }

    @Test
    void whenTransferAmount_givenSourceUserOfTypeShopper_thenThrowBusinessException() {
        when(sourceUser.isShopper()).thenReturn(true);
        assertThrows(BusinessException.class, () -> {
            this.transferAmount.invoke(1L, 2L, BigDecimal.ZERO);
        });
    }

    @Test
    void whenTransferAmount_givenInvalidSourceId_thenThrowResourceNotFoundException() {
        when(this.findUserById.findUserById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            this.transferAmount.invoke(1L, 2L, BigDecimal.ZERO);
        });
    }

    @Test
    void whenTransferAmount_givenValidSourceId_thenCallRepository() {
        this.transferAmount.invoke(1L, 2L, BigDecimal.ZERO);
        verify(this.findUserById, times(1)).findUserById(1L);
    }

    @Test
    void whenTransferAmount_givenInvalidDestinationId_thenThrowResourceNotFoundException() {
        when(this.findUserById.findUserById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            this.transferAmount.invoke(1L, 2L, BigDecimal.ZERO);
        });
    }

    @Test
    void whenTransferAmount_givenValidDestinationId_thenCallRepository() {
        this.transferAmount.invoke(1L, 2L, BigDecimal.ZERO);
        verify(this.findUserById, times(1)).findUserById(2L);
    }

}
