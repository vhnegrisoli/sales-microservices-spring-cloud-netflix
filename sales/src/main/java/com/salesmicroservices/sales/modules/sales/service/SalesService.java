package com.salesmicroservices.sales.modules.sales.service;

import com.salesmicroservices.sales.config.exception.ValidationException;
import com.salesmicroservices.sales.modules.jwt.service.AuthUserService;
import com.salesmicroservices.sales.modules.product.dto.ProductSuccessResponse;
import com.salesmicroservices.sales.modules.product.service.ProductService;
import com.salesmicroservices.sales.modules.sales.document.Sales;
import com.salesmicroservices.sales.modules.sales.dto.*;
import com.salesmicroservices.sales.modules.sales.enums.SalesStatus;
import com.salesmicroservices.sales.modules.sales.rabbitmq.SalesSender;
import com.salesmicroservices.sales.modules.sales.repository.SalesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class SalesService {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private SalesSender salesSender;

    public Mono<SalesResponse> save(SalesRequest request) {
        validateSalesRequest(request);
        validateSalesRequestWithProductsAndQuantity(request);
        var products = productService.findProductsByIds(getProductsIds(request));
        validateExistingProducts(products);
        var authUser = authUserService.getAuthUser();
        var sales = Sales.convertFrom(request, products.getProductsResponse(), authUser);
        salesRepository.save(sales).block();
        sendProductsToStockUpdate(sales);
        return Mono.just(SalesResponse.convertFrom(sales));
    }

    public Mono<SalesResponse> findSalesResponseById(String salesId) {
        return Mono
            .just(findById(salesId))
            .map(SalesResponse::convertFrom);
    }

    public Flux<SalesResponse> findAll() {
        return salesRepository
            .findAll()
            .flatMap(this::toSalesToMonoSalesResponse);
    }

    private Mono<SalesResponse> toSalesToMonoSalesResponse(Sales sales) {
        return Mono.just(SalesResponse.convertFrom(sales));
    }

    private Sales findById(String salesId) {
        return salesRepository.findById(salesId)
            .switchIfEmpty(Mono.error(
                new ValidationException("The salesID "
                    .concat(salesId)
                    .concat(" was not found.")))
            )
            .block();
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

    private void sendProductsToStockUpdate(Sales sales) {
        salesSender
            .sendProductStockUpdateMessage(new ProductMqRequest(
                sales
                    .getProducts()
                    .stream()
                    .map(message -> ProductStockMessage.convertFrom(sales.getId(), message))
                    .collect(Collectors.toList()))
            );
    }

    public void confirmSales(SalesConfirmationMessage message) {
        if (isValidSalesConfirmationMessage(message)) {
            var sales = findById(message.getSalesId());
            defineConfirmedStatus(sales, message);
            defineCanceledStatus(sales, message);
            defineSalesCancelationCause(sales, message);
            sales.setLastUpdate(LocalDateTime.now());
            salesRepository.save(sales).subscribe();
        } else {
            log.error("Nothing was proccessed because recieved message is invalid.");
        }
    }

    private void defineConfirmedStatus(Sales sales, SalesConfirmationMessage message) {
        if (message.getConfirmed() && sales.isPending()) {
            sales.setStatus(SalesStatus.CONFIRMED);
        }
    }

    private void defineCanceledStatus(Sales sales, SalesConfirmationMessage message) {
        if (!message.getConfirmed() && !sales.isCanceled()) {
            sales.setStatus(SalesStatus.CANCELED);
        }
    }

    private void defineSalesCancelationCause(Sales sales, SalesConfirmationMessage message) {
        if (!isEmpty(message.getCause())) {
            sales.getCancelationCause().add(message.getCause());
        }
    }

    private boolean isValidSalesConfirmationMessage(SalesConfirmationMessage message) {
        return !isEmpty(message)
            && !isEmpty(message.getSalesId())
            && !isEmpty(message.getConfirmed());
    }
}
