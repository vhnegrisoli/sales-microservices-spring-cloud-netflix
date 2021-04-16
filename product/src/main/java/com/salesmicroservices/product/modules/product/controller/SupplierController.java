package com.salesmicroservices.product.modules.product.controller;

import com.salesmicroservices.product.config.SuccessResponse;
import com.salesmicroservices.product.modules.product.dto.SupplierDto;
import com.salesmicroservices.product.modules.product.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public SupplierDto save(@RequestBody SupplierDto request) {
        return supplierService.save(request);
    }

    @PutMapping("{supplierId}")
    public SupplierDto update(@RequestBody SupplierDto request,
                              @PathVariable Integer supplierId) {
        return supplierService.update(request, supplierId);
    }

    @DeleteMapping("{supplierId}")
    public SuccessResponse delete(@PathVariable Integer supplierId) {
        return supplierService.delete(supplierId);
    }

    @GetMapping("{supplierId}")
    public SupplierDto findById(@PathVariable Integer supplierId) {
        return supplierService.findById(supplierId);
    }

    @GetMapping
    public List<SupplierDto> findAll() {
        return supplierService.findAll();
    }

    @GetMapping("name/{name}")
    public List<SupplierDto> findByName(@PathVariable String name) {
        return supplierService.findByName(name);
    }

    @GetMapping("ein-cnpj/{einCnpj}")
    public List<SupplierDto> findByEinCnpj(@PathVariable String einCnpj) {
        return supplierService.findByEinCnpj(einCnpj);
    }
}
