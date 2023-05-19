package com.project.transferapi.interfaces.outbound.amqp.config;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpProducerConfig {

    @Value("${spring.rabbitmq.notifyTransactionExchange}")
    private String exchange;

    @Bean
    public FanoutExchange requiredActionsExchange() {
        return ExchangeBuilder.fanoutExchange(this.exchange).build();
    }

}
