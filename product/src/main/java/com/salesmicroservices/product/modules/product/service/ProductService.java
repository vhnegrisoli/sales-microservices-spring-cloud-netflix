package com.salesmicroservices.product.modules.product.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmicroservices.product.config.exception.ValidationException;
import com.salesmicroservices.product.modules.product.dto.ProductResponse;
import com.salesmicroservices.product.modules.product.dto.ProductStockMessage;
import com.salesmicroservices.product.modules.product.dto.SalesConfirmatioMessage;
import com.salesmicroservices.product.modules.product.model.Product;
import com.salesmicroservices.product.modules.product.rabbitmq.ProductSender;
import com.salesmicroservices.product.modules.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSender productSender;

    public List<ProductResponse> findAll() {
        return productRepository
            .findAll()
            .stream()
            .map(ProductResponse::convertFrom)
            .collect(Collectors.toList());
    }

    public ProductResponse findProductResponseById(Integer id) {
        return ProductResponse.convertFrom(findById(id));
    }

    private Product findById(Integer id) {
        return productRepository
            .findById(id)
            .orElseThrow(() -> new ValidationException("The product ID "
            .concat(String.valueOf(id))
            .concat(" does not exists.")));
    }

    public boolean existsByCategoryId(Integer categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }

    public boolean existsBySupplierId(Integer supplierId) {
        return productRepository.existsBySupplierId(supplierId);
    }

    public void updateStock(ProductStockMessage message) {
        if (isValidMessage(message)) {
            var product = findById(message.getProductId());
            var salesConfirmatioMessage = defineSalesConfirmationMessage(message, product);
            productSender.sendSalesConfirmationMessage(salesConfirmatioMessage);
        } else {
            log.error("Nothing was proccessed because recieved message is invalid.");
        }
    }

    private boolean isValidMessage(ProductStockMessage message) {
        return !isEmpty(message)
            && !isEmpty(message.getProductId())
            && !isEmpty(message.getQuantity());
    }

    private SalesConfirmatioMessage defineSalesConfirmationMessage(ProductStockMessage message, Product product) {
        var salesConfirmatioMessage = new SalesConfirmatioMessage();
        salesConfirmatioMessage.setSalesId(message.getSalesId());
        if (product.getQuantityAvailable() < message.getQuantity()) {
            var salesFailMessage = createSalesFailMessage(message, product);
            log.error(salesFailMessage);
            salesConfirmatioMessage.setCause(salesFailMessage);
            salesConfirmatioMessage.setConfirmed(false);
        } else {
            product.updateStock(message.getQuantity());
            productRepository.save(product);
            salesConfirmatioMessage.setConfirmed(true);
        }
        return salesConfirmatioMessage;
    }

    private String createSalesFailMessage(ProductStockMessage message, Product product) {
        return "It's not possible to update product stock. The actual quantity available is "
            .concat(String.valueOf(product.getQuantityAvailable()))
            .concat(" and the informed sales quantity is ")
            .concat(String.valueOf(message.getQuantity()))
            .concat(" for salesId ")
            .concat(message.getSalesId());
    }
}
