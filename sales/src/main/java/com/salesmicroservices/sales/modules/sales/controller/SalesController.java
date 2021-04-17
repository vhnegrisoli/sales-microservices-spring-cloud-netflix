package com.salesmicroservices.sales.modules.sales.controller;

import com.salesmicroservices.sales.modules.sales.dto.SalesRequest;
import com.salesmicroservices.sales.modules.sales.dto.SalesResponse;
import com.salesmicroservices.sales.modules.sales.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
