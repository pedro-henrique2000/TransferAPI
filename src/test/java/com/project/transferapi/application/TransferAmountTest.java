package com.project.transferapi.application;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.exceptions.ResourceNotFoundException;
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
    User sourceUser;

    @Mock
    User destinationUser;

    @BeforeEach
    void setup() {
        lenient().when(this.findUserById.findUserById(1L)).thenReturn(Optional.of(sourceUser));
        lenient().when(this.findUserById.findUserById(2L)).thenReturn(Optional.of(destinationUser));
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
