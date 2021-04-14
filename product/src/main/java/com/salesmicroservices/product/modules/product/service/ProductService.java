package com.salesmicroservices.product.modules.product.service;

import com.salesmicroservices.product.modules.product.dto.ProductResponse;
import com.salesmicroservices.product.modules.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponse> findAll() {
        return productRepository
            .findAll()
            .stream()
            .map(ProductResponse::convertFrom)
            .collect(Collectors.toList());
    }
}
