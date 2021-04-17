package com.salesmicroservices.product.modules.product.service;

import com.salesmicroservices.product.config.SuccessResponse;
import com.salesmicroservices.product.config.exception.ValidationException;
import com.salesmicroservices.product.modules.product.dto.*;
import com.salesmicroservices.product.modules.product.model.Product;
import com.salesmicroservices.product.modules.product.rabbitmq.ProductSender;
import com.salesmicroservices.product.modules.product.repository.ProductRepository;
import com.salesmicroservices.product.modules.sales.dto.SalesSuccessResponse;
import com.salesmicroservices.product.modules.sales.service.SalesService;
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

    @Autowired
    private SalesService salesService;

    public ProductResponse save(ProductRequest request) {
        validateProductRequest(request);
        validateProductData(request, false);
        var product = Product.convertFrom(request);
        productRepository.save(product);
        return findProductResponseById(product.getId());
    }

    public ProductResponse update(ProductRequest request, Integer id) {
        validateProductRequest(request);
        request.setId(id);
        validateProductData(request, true);
        var product = Product.convertFrom(request);
        product.setId(id);
        productRepository.save(product);
        return findProductResponseById(product.getId());
    }

    public SuccessResponse delete(Integer id) {
        validateExistingProductById(id);
        validateExistingProductForSales(id);
        productRepository.deleteById(id);
        return SuccessResponse.sendSuccess("The product "
            .concat(String.valueOf(id))
            .concat(" was deleted successfully!"));
    }

    public List<ProductResponse> findAll() {
        return productRepository
            .findAll()
            .stream()
            .map(ProductResponse::convertFrom)
            .collect(Collectors.toList());
    }

    private void validateExistingProductById(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new ValidationException("The productId "
                .concat(String.valueOf(id))
                .concat(" does not exists."));
        }
    }

    private void validateExistingProductForSales(Integer id) {
        var sales = salesService.findSalesByProductId(id);
        validateSuccessSalesResponse(sales);
        if (!isEmpty(sales.getSalesResponse())) {
            throw new ValidationException("It's not possible to delete the product "
                .concat(String.valueOf(id))
                .concat(". There are products available for sales."));
        }
    }

    private void validateProductRequest(ProductRequest request) {
        if (isEmpty(request)) {
            throw new ValidationException("The product request must not be empty.");
        }
    }

    private void validateProductData(ProductRequest request, boolean isUpdate) {
        validateProductFields(request, isUpdate);
        validateExistingProduct(request, isUpdate);
    }

    private void validateProductFields(ProductRequest request, boolean isUpdate) {
        if (isUpdate && isEmpty(request.getId())) {
            throw new ValidationException("The ID must not be empty when updating a product.");
        }
        if (isEmpty(request.getQuantityAvailable())
            || isEmpty(request.getPrice())
            || isEmpty(request.getProductDescription())
            || isEmpty(request.getCategoryId())
            || isEmpty(request.getSupplierId())) {
            throw new ValidationException("All fields must be informed.");
        }
    }

    public void validateExistingProduct(ProductRequest request, boolean isUpdate) {
        if (!isUpdate && productRepository.existsByDescription(request.getProductDescription())) {
            throw new ValidationException("The product "
                .concat(request.getProductDescription())
                .concat(" already exists."));
        }
        if (isUpdate
            && productRepository.existsByDescriptionAndIdNot(request.getProductDescription(), request.getId())) {
            throw new ValidationException("The product "
                .concat(request.getProductDescription())
                .concat(" already exists for another product ID."));
        }
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

    public List<ProductResponse> findByDescription(String productDescription) {
        return productRepository
            .findByDescriptionContainingIgnoreCase(productDescription)
            .stream()
            .map(ProductResponse::convertFrom)
            .collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategoryId(Integer categoryId) {
        return productRepository
            .findByCategoryId(categoryId)
            .stream()
            .map(ProductResponse::convertFrom)
            .collect(Collectors.toList());
    }

    public List<ProductResponse> findBySupplierId(Integer supplierId) {
        return productRepository
            .findBySupplierId(supplierId)
            .stream()
            .map(ProductResponse::convertFrom)
            .collect(Collectors.toList());
    }

    public List<ProductResponse> findByIds(List<Integer> productsIds) {
        return productRepository
            .findByIdIn(productsIds)
            .stream()
            .map(ProductResponse::convertFrom)
            .collect(Collectors.toList());
    }

    public ProductBySales findTotalSalesByProductId(Integer productId) {
        var sales = salesService.findSalesByProductId(productId);
        validateSuccessSalesResponse(sales);
        return ProductBySales.convertFrom(sales.getSalesResponse(), productId);
    }

    public void updateStock(ProductMqResponse message) {
        if (!isEmpty(message) && !isEmpty(message.getSalesProducts())) {
            message
                .getSalesProducts()
                .forEach(this::processProductUpdateStock);
        }
    }

    private void processProductUpdateStock(ProductStockMessage message) {
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
            && !isEmpty(message.getSalesId())
            && !isEmpty(message.getProductId())
            && !isEmpty(message.getQuantity());
    }

    private SalesConfirmationMessage defineSalesConfirmationMessage(ProductStockMessage message, Product product) {
        var salesConfirmatioMessage = new SalesConfirmationMessage();
        salesConfirmatioMessage.setSalesId(message.getSalesId());
        if (product.getQuantityAvailable() >= message.getQuantity()) {
            updateProductStock(product, message.getQuantity());
            salesConfirmatioMessage.setConfirmed(true);
        } else {
            var salesFailMessage = createSalesFailMessage(message, product);
            log.error(salesFailMessage);
            salesConfirmatioMessage.setCause(salesFailMessage);
            salesConfirmatioMessage.setConfirmed(false);
        }
        return salesConfirmatioMessage;
    }

    private void updateProductStock(Product product, Integer quantity) {
        product.updateStock(quantity);
        productRepository.save(product);
    }

    private String createSalesFailMessage(ProductStockMessage message, Product product) {
        return "It's not possible to update product stock. The actual quantity available is "
            .concat(String.valueOf(product.getQuantityAvailable()))
            .concat(" and the informed sales quantity is ")
            .concat(String.valueOf(message.getQuantity()))
            .concat(" for salesId ")
            .concat(message.getSalesId());
    }

    public boolean existsByCategoryId(Integer categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }

    public boolean existsBySupplierId(Integer supplierId) {
        return productRepository.existsBySupplierId(supplierId);
    }

    private void validateSuccessSalesResponse(SalesSuccessResponse sales) {
        if (!sales.isSuccess()) {
            throw new ValidationException("It was not possible to proccess your data in Sales API.");
        }
    }
}
