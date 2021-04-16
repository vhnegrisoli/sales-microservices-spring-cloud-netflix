package com.salesmicroservices.product.modules.sales.service;

import com.salesmicroservices.product.config.LogRequest;
import com.salesmicroservices.product.config.LogResponse;
import com.salesmicroservices.product.modules.sales.client.SalesClient;
import com.salesmicroservices.product.modules.sales.dto.SalesResponse;
import com.salesmicroservices.product.modules.sales.dto.SalesSuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class SalesService {

    @Autowired
    private SalesClient salesClient;

    @Value("${client.service.sales.url}")
    private String service;

    public SalesSuccessResponse findSalesByProductId(Integer productId) {
        try {
            logRequest("product/{productId}", productId);
            var sales = salesClient.findSalesByProduct(productId);
            logResponse(sales);
            return SalesSuccessResponse.createSuccessResponse(sales);
        } catch (Exception ex) {
            log.error("Error while trying to get Sales by productId: ".concat(String.valueOf(productId)), ex);
            return SalesSuccessResponse.createFailResponse();
        }
    }

    private void logRequest(String url, Integer productId) {
        var serviceUrl = service.concat(url).concat(String.valueOf(productId));
        log.info(LogRequest.create(serviceUrl, HttpMethod.GET, null, String.valueOf(productId)).toJson());
    }

    private void logResponse(Object sales) {
        log.info(LogResponse.create(sales, HttpStatus.OK.value()).toJson());
    }
}
