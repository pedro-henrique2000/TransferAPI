package com.project.transferapi.interfaces.inbound.amqp.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConsumerConfig {

   @Value("${spring.rabbitmq.notifyTransactionQueue}")
   private String queue;

   @Value("${spring.rabbitmq.notifyTransactionExchange}")
   private String exchange;

   @Bean
   public FanoutExchange exchange() {
      return ExchangeBuilder.fanoutExchange(this.exchange).build();
   }

   @Bean
   public Queue queue() {
      return new Queue(queue, true);
   }

   @Bean
   public Binding binding() {
      return BindingBuilder.bind(this.queue()).to(this.exchange());
   }

}
