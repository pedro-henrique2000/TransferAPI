package com.project.transferapi.interfaces.inbound.amqp.consumer;

import com.project.transferapi.application.TransferNotificator;
import com.project.transferapi.interfaces.outbound.dto.TransferNotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferNotificationConsumer {

    private final TransferNotificator transferNotificator;

    @RabbitListener(queues = "${spring.rabbitmq.notifyTransactionQueue}", concurrency = "1")
    public void consume(@Payload TransferNotificationDTO transferNotificationDTO) {
        log.info("TransferNotificationConsumer::consume - Received Transaction with source user {} ", transferNotificationDTO.getSourceName());
        this.transferNotificator.invoke(transferNotificationDTO.getSourceName(),
                transferNotificationDTO.getDestinationName(),
                transferNotificationDTO.getAmount(),
                transferNotificationDTO.getStatus());
    }

}
