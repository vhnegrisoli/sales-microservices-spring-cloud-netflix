package com.salesmicroservices.sales.config;

import com.salesmicroservices.sales.modules.jwt.dto.AuthUser;
import com.salesmicroservices.sales.modules.product.dto.CategoryResponse;
import com.salesmicroservices.sales.modules.product.dto.ProductResponse;
import com.salesmicroservices.sales.modules.product.dto.SupplierResponse;
import com.salesmicroservices.sales.modules.sales.document.Sales;
import com.salesmicroservices.sales.modules.sales.document.SalesBuyer;
import com.salesmicroservices.sales.modules.sales.document.SalesProduct;
import com.salesmicroservices.sales.modules.sales.enums.SalesStatus;
import com.salesmicroservices.sales.modules.sales.repository.SalesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class InitialDataConfig {

    @Autowired
    private SalesRepository salesRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void insertInitialDataToMongoDB() {
        var buyer = SalesBuyer.convertFrom(new AuthUser(1, "test_user", Collections.emptyList()));
        salesRepository.deleteAll().block();
        log.info("Deleting existing data...");
        var pending = SalesStatus.PENDING;
        log.info("Creating initial data...");
        salesRepository
            .saveAll(List.of(Sales
                    .builder()
                    .createdAt(LocalDateTime.now())
                    .lastUpdate(LocalDateTime.now())
                    .status(pending)
                    .products(List.of(
                        new SalesProduct(createProductResponse(1000, "Man of Steel", 45.9, 5, "Comic Books","Amazon"), 3),
                        new SalesProduct(createProductResponse(1001, "Spider-Man 2", 25.9, 2, "Movies","Ebay"), 2),
                        new SalesProduct(createProductResponse(1002, "Man of Steel", 45.9, 3, "Comic Books","Panini"), 1)
                    ))
                    .buyer(buyer)
                    .build(),
                Sales
                    .builder()
                    .createdAt(LocalDateTime.now())
                    .lastUpdate(LocalDateTime.now())
                    .status(pending)
                    .products(List.of(
                        new SalesProduct(createProductResponse(1002, "Man of Steel", 45.9, 5, "Comic Books","Amazon"), 3),
                        new SalesProduct(createProductResponse(1003, "Harry Potter", 55.85, 5, "Books","Ebay"), 1)
                    ))
                    .buyer(buyer)
                    .build()
                )
            )
            .subscribe();
        log.info("Initial data created successfully!");
    }

    private ProductResponse createProductResponse(Integer id,
                                                  String name,
                                                  Double price,
                                                  Integer quantity,
                                                  String category,
                                                  String supplier) {
        return ProductResponse
            .builder()
            .productId(id)
            .productDescription(name)
            .price(price)
            .category(CategoryResponse
                .builder()
                .id(id)
                .description(category)
                .build())
            .quantityAvailable(quantity)
            .supplier(SupplierResponse
                .builder()
                .id(id)
                .name(supplier)
                .einCnpj("598181818")
                .build())
            .build();
    }
}
