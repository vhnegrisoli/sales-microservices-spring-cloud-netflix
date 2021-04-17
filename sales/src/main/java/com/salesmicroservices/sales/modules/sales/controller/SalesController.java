package com.salesmicroservices.sales.modules.sales.controller;

import com.salesmicroservices.sales.modules.sales.dto.SalesByProductResponse;
import com.salesmicroservices.sales.modules.sales.dto.SalesRequest;
import com.salesmicroservices.sales.modules.sales.dto.SalesResponse;
import com.salesmicroservices.sales.modules.sales.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @PostMapping
    public Mono<SalesResponse> save(@RequestBody SalesRequest request) {
        return salesService.save(request);
    }

    @GetMapping
    public Flux<SalesResponse> findAll() {
        return salesService.findAll();
    }

    @GetMapping("{salesId}")
    public Mono<SalesResponse> findById(@PathVariable String salesId) {
        return salesService.findSalesResponseById(salesId);
    }

    @GetMapping("product/{productId}")
    public Flux<SalesByProductResponse> findByProductId(@PathVariable Integer productId) {
        return salesService.findByProductId(productId);
    }
}
