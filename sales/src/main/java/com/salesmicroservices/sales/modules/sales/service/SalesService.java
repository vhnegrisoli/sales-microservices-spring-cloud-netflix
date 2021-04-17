package com.salesmicroservices.sales.modules.sales.service;

import com.salesmicroservices.sales.config.exception.ValidationException;
import com.salesmicroservices.sales.modules.product.dto.ProductSuccessResponse;
import com.salesmicroservices.sales.modules.product.service.ProductService;
import com.salesmicroservices.sales.modules.sales.document.Sales;
import com.salesmicroservices.sales.modules.sales.dto.SalesProductRequest;
import com.salesmicroservices.sales.modules.sales.dto.SalesRequest;
import com.salesmicroservices.sales.modules.sales.dto.SalesResponse;
import com.salesmicroservices.sales.modules.sales.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class SalesService {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private ProductService productService;

    public Mono<SalesResponse> save(SalesRequest request) {
        validateSalesRequest(request);
        validateSalesRequestWithProductsAndQuantity(request);
        var products = productService.findProductsByIds(getProductsIds(request));
        validateExistingProducts(products);
        var sales = Sales.convertFrom(request, products.getProductsResponse());
        salesRepository.save(sales);
        return Mono.just(SalesResponse.convertFrom(sales));
    }

    private void validateSalesRequest(SalesRequest request) {
        if (isEmpty(request)) {
            throw new ValidationException("The sales request must not be empty.");
        }
    }

    private void validateSalesRequestWithProductsAndQuantity(SalesRequest request) {
        if (isEmpty(request.getProducts())) {
            throw new ValidationException("The products must be informed for sales.");
        }
        if (existsSalesProductsWithoutProductIdOrQuantity(request)) {
            throw new ValidationException("The productId and the quantity must be informed.");
        }
    }

    private boolean existsSalesProductsWithoutProductIdOrQuantity(SalesRequest request) {
        return request
            .getProducts()
            .stream()
            .anyMatch(product -> isEmpty(product.getQuantity()) || isEmpty(product.getProductId()));
    }

    private List<Integer> getProductsIds(SalesRequest salesRequest) {
        return salesRequest
            .getProducts()
            .stream()
            .map(SalesProductRequest::getProductId)
            .collect(Collectors.toList());
    }

    private void validateExistingProducts(ProductSuccessResponse products) {
        if (!products.isSuccess() || isEmpty(products.getProductsResponse())) {
            throw new ValidationException("It was not possible to verify the products.");
        }
    }
}
