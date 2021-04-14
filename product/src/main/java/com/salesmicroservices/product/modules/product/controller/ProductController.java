package com.salesmicroservices.product.modules.product.controller;

import com.salesmicroservices.product.modules.product.dto.ProductRequest;
import com.salesmicroservices.product.modules.product.dto.ProductResponse;
import com.salesmicroservices.product.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request) {
        return null;
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }
}
