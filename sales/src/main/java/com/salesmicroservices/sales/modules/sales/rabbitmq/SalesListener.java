package com.salesmicroservices.sales.modules.sales.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmicroservices.sales.modules.sales.dto.SalesConfirmationMessage;
import com.salesmicroservices.sales.modules.sales.service.SalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SalesListener {

    @Autowired
    private SalesService salesService;

    @Value("${rabbit.queue.product-sales-confirmation}")
    private String productSalesConfirmation;

    @RabbitListener(queues = "${rabbit.queue.product-sales-confirmation}")
    public void recieveMessageFromProductSalesConfirmation(SalesConfirmationMessage message) {
        try {
            log.info("Data recieved: "
                .concat(new ObjectMapper().writeValueAsString(message))
                .concat("\nfrom Queue: ")
                .concat(productSalesConfirmation)
            );
            salesService.confirmSales(message);
        } catch (Exception ex) {
            log.error("Error while recieving message.", ex);
        }
    }

}
