package com.project.transferapi.interfaces.outbound.amqp.publisher;

import com.project.transferapi.domain.entity.Transaction;
import com.project.transferapi.domain.ports.IPublishTransferNotification;
import com.project.transferapi.interfaces.outbound.dto.TransferNotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
class TransferNotificationPublisher implements IPublishTransferNotification {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.notifyTransactionExchange}")
    private String exchange;

    @Override
    public void notify(Transaction transaction) {
        log.info("TransferNotificationPublisher::notify - Publishing message for transaction with id {} and Source Name {}", transaction.getId(), transaction.getSource().getFullName());
        this.rabbitTemplate.convertAndSend(
                this.exchange,
                "",
                TransferNotificationDTO.builder()
                        .sourceName(transaction.getSource().getFullName())
                        .destinationName(transaction.getDestination().getFullName())
                        .amount(transaction.getAmount())
                        .status(transaction.getStatus().toString())
                        .build()
        );
    }
}
