package com.salesmicroservices.product.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbit.exchange.product}")
    private String productTopicExchange;

    @Value("${rabbit.queue.product-stock}")
    private String updateStockMq;

    @Value("${rabbit.routingkey.product-stock}")
    private String updateStockKey;

    @Value("${rabbit.queue.product-sales-confirmation}")
    private String productSalesConfirmationMq;

    @Value("${rabbit.routingkey.product-sales-confirmation}")
    private String productSalesConfirmationKey;

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(productTopicExchange);
    }

    @Bean
    Queue updateStockMq() {
        return new Queue(updateStockMq, true);
    }

    @Bean
    Queue productSalesConfirmationMq() {
        return new Queue(productSalesConfirmationMq, true);
    }

    @Bean
    public Binding updateStockMqBinding(TopicExchange exchange) {
        return BindingBuilder
            .bind(updateStockMq())
            .to(exchange)
            .with(updateStockKey);
    }

    @Bean
    public Binding productSalesConfirmationMqBinding(TopicExchange exchange) {
        return BindingBuilder
            .bind(productSalesConfirmationMq())
            .to(exchange)
            .with(productSalesConfirmationKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
