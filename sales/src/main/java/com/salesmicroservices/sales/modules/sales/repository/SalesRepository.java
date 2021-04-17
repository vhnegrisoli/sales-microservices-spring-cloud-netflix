package com.salesmicroservices.sales.modules.sales.repository;

import com.salesmicroservices.sales.modules.sales.document.Sales;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SalesRepository extends ReactiveMongoRepository<Sales, String> {
}
