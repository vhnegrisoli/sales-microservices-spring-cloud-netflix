package com.salesmicroservices.product.modules.sales.client;

import com.salesmicroservices.product.modules.sales.dto.SalesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
    url = "${client.service.sales.url}",
    name = "SalesClient",
    contextId = "SalesClient"
)
public interface SalesClient {

    @GetMapping("product/{productId}")
    List<SalesResponse> findSalesByProduct(@PathVariable Integer productId);
}
