package com.salesmicroservices.sales.modules.product.client;

import com.salesmicroservices.sales.modules.product.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
    url = "${client.service.product.url}",
    name = "ProductClient",
    contextId = "ProductClient"
)
public interface ProductClient {

    @GetMapping("ids")
    List<ProductResponse> findByIds(@RequestParam(name = "ids") List<Integer> productsIds);
}
