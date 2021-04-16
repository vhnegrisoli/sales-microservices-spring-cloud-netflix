package com.salesmicroservices.product.modules.product.service;

import com.salesmicroservices.product.config.SuccessResponse;
import com.salesmicroservices.product.config.exception.ValidationException;
import com.salesmicroservices.product.modules.product.dto.ProductRequest;
import com.salesmicroservices.product.modules.product.dto.SupplierDto;
import com.salesmicroservices.product.modules.product.model.Supplier;
import com.salesmicroservices.product.modules.product.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductService productService;

    public SupplierDto save(SupplierDto request) {
        validateSupplierRequest(request);
        validateSupplierData(request, false);
        var supplier = Supplier.convertFrom(request);
        supplierRepository.save(supplier);
        return findById(supplier.getId());
    }

    public SupplierDto update(SupplierDto request, Integer supplierId) {
        validateSupplierRequest(request);
        request.setId(supplierId);
        validateSupplierData(request, true);
        var supplier = Supplier.convertFrom(request);
        supplier.setId(supplierId);
        supplierRepository.save(supplier);
        return findById(supplier.getId());
    }

    public SuccessResponse delete(Integer supplierId) {
        validateSupplierExistingForProducts(supplierId);
        supplierRepository.deleteById(supplierId);
        return SuccessResponse
            .sendSuccess("Supplier "
                .concat(String.valueOf(supplierId))
                .concat(" was deleted successfully!")
            );
    }

    public SupplierDto findById(Integer supplierId) {
        return SupplierDto.convertFrom(supplierRepository
            .findById(supplierId)
                .orElseThrow(() -> new ValidationException(
                    "The supplierId "
                        .concat(String.valueOf(supplierId))
                        .concat(" does not exists."))
                )
        );
    }

    public List<SupplierDto> findAll() {
        return supplierRepository
            .findAll()
            .stream()
            .map(SupplierDto::convertFrom)
            .collect(Collectors.toList());
    }

    public List<SupplierDto> findByName(String name) {
        return supplierRepository
            .findByNameContainingIgnoreCase(name)
            .stream()
            .map(SupplierDto::convertFrom)
            .collect(Collectors.toList());
    }

    public List<SupplierDto> findByEinCnpj(String einCnpj) {
        return supplierRepository
            .findByEinCnpjContainingIgnoreCase(einCnpj)
            .stream()
            .map(SupplierDto::convertFrom)
            .collect(Collectors.toList());
    }

    private void validateSupplierRequest(SupplierDto request) {
        if (isEmpty(request)) {
            throw new ValidationException("The supplier request must not be empty.");
        }
    }

    private void validateSupplierData(SupplierDto request, boolean isUpdate) {
        validateSupplierFields(request, isUpdate);
        validateExistingSupplier(request, isUpdate);
    }

    private void validateSupplierFields(SupplierDto request, boolean isUpdate) {
        if (isUpdate && isEmpty(request.getId())) {
            throw new ValidationException("The ID must not be empty when updating a supplier.");
        }
        if (isEmpty(request.getName())
            || isEmpty(request.getEinCnpj())) {
            throw new ValidationException("All fields must be informed.");
        }
    }

    public void validateExistingSupplier(SupplierDto request, boolean isUpdate) {
        if (!isUpdate && supplierRepository.existsByEinCnpj(request.getEinCnpj())) {
            throw new ValidationException("The supplier ein/cnpj "
                .concat(request.getEinCnpj())
                .concat(" already exists."));
        }
        if (isUpdate
            && supplierRepository.existsByEinCnpjAndIdNot(request.getEinCnpj(), request.getId())) {
            throw new ValidationException("The supplier ein/cnpj "
                .concat(request.getEinCnpj())
                .concat(" already exists for another supplier ID."));
        }
    }

    private void validateSupplierExistingForProducts(Integer supplierId) {
        if (!supplierRepository.existsById(supplierId)) {
            throw new ValidationException("The supplier ID "
                .concat(String.valueOf(supplierId))
                .concat(" does not exists."));
        }
        if (productService.existsBySupplierId(supplierId)) {
            throw new ValidationException("The supplier ID "
                .concat(String.valueOf(supplierId))
                .concat(" already exists for a product."));
        }
    }
}
