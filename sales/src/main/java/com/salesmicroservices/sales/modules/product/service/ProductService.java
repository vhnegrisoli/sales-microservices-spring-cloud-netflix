package com.salesmicroservices.sales.modules.product.service;

import com.salesmicroservices.sales.config.LogRequest;
import com.salesmicroservices.sales.config.LogResponse;
import com.salesmicroservices.sales.modules.product.client.ProductClient;
import com.salesmicroservices.sales.modules.product.dto.ProductSuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductClient productClient;

    @Value("${client.service.product.url}")
    private String service;

    public ProductSuccessResponse findProductsByIds(List<Integer> productsIds) {
        try {
            logRequest("/ids", productsIds);
            var products = productClient.findByIds(productsIds);
            logResponse(products);
            return ProductSuccessResponse.createSuccessResponse(products);
        } catch (Exception ex) {
            log.error("Error while trying to get products by ids: ".concat(productsIds.toString()), ex);
            return ProductSuccessResponse.createFailResponse();
        }
    }

    private void logRequest(String url, List<Integer> productsIds) {
        var serviceUrl = service.concat(url).concat(productsIds.toString());
        log.info(LogRequest.create(serviceUrl, HttpMethod.GET, null, productsIds.toString()).toJson());
    }

    private void logResponse(Object sales) {
        log.info(LogResponse.create(sales, HttpStatus.OK.value()).toJson());
    }
}
