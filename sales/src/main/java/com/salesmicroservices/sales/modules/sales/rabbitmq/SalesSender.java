package com.salesmicroservices.sales.modules.sales.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmicroservices.sales.modules.sales.dto.ProductMqRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SalesSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbit.exchange.product}")
    private String productTopicExchange;

    @Value("${rabbit.routingkey.product-stock}")
    private String productStockRoutingkey;

    public void sendProductStockUpdateMessage(ProductMqRequest message) {
        var topicRoutingKeyLogMessage = getTopicRoutingKeyLogMessage(productTopicExchange, productStockRoutingkey);
        try {
            rabbitTemplate.convertAndSend(productTopicExchange, productStockRoutingkey, message);
            log.info("Sending data: "
                .concat(new ObjectMapper().writeValueAsString(message))
                .concat(topicRoutingKeyLogMessage)
            );
        } catch (Exception ex) {
            log.error("Error while trying to send data.".concat(topicRoutingKeyLogMessage), ex);
        }
    }

    private String getTopicRoutingKeyLogMessage(String topic, String routingKey) {
        return "\nTopic: "
            .concat(topic)
            .concat("\nRouting key: ")
            .concat(routingKey);
    }
}
