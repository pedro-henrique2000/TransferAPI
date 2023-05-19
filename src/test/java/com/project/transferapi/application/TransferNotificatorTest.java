package com.project.transferapi.application;

import com.project.transferapi.domain.ports.IExternalTransactionNotifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferNotificatorTest {

    @InjectMocks
    TransferNotificator notificator;

    @Mock
    IExternalTransactionNotifier externalTransactionNotifier;

    @Test
    void shouldCallExternalTransactionNotifier() {
        notificator.invoke("source", "dest", BigDecimal.ONE, "status");
        verify(this.externalTransactionNotifier, times(1)).sendNotification("source", "dest", BigDecimal.ONE, "status");
    }


}
