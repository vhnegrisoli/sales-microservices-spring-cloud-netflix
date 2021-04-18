package com.salesmicroservices.sales.modules.sales.repository;

import com.salesmicroservices.sales.modules.sales.document.Sales;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface SalesRepository extends ReactiveMongoRepository<Sales, String> {

    @Query(value = "{ 'products': { $elemMatch: { 'product.productId': ?0 } } }")
    Flux<Sales> findByProductId(Integer productId);
}
