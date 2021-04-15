package com.salesmicroservices.product.modules.product.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmicroservices.product.modules.product.dto.ProductStockMessage;
import com.salesmicroservices.product.modules.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ProductListener {

    @Autowired
    private ProductService productService;

    @Value("${rabbit.queue.product-stock}")
    private String productStockQueue;

    @RabbitListener(queues = "${rabbit.queue.product-stock}")
    public void recieveMessageFromProductStockQueue(ProductStockMessage message) {
        try {
            log.info("Data recieved: "
                .concat(new ObjectMapper().writeValueAsString(message))
                .concat("\nfrom Queue: ")
                .concat(productStockQueue)
            );
            productService.updateStock(message);
        } catch (Exception ex) {
            log.error("Error while recieving message.", ex);
        }
    }
}
