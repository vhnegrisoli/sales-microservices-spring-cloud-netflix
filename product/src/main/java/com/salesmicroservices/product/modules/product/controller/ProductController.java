package com.salesmicroservices.product.modules.product.controller;

import com.salesmicroservices.product.config.SuccessResponse;
import com.salesmicroservices.product.modules.product.dto.ProductBySales;
import com.salesmicroservices.product.modules.product.dto.ProductRequest;
import com.salesmicroservices.product.modules.product.dto.ProductResponse;
import com.salesmicroservices.product.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request) {
        return productService.save(request);
    }

    @PutMapping("{productId}")
    public ProductResponse update(@RequestBody ProductRequest request,
                                  @PathVariable Integer productId) {
        return productService.update(request, productId);
    }

    @DeleteMapping("{productId}")
    public SuccessResponse delete(@PathVariable Integer productId) {
        return productService.delete(productId);
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping("{productId}")
    public ProductResponse findById(@PathVariable Integer productId) {
        return productService.findProductResponseById(productId);
    }

    @GetMapping("description/{productDescription}")
    public List<ProductResponse> findByDescription(@PathVariable String productDescription) {
        return productService.findByDescription(productDescription);
    }

    @GetMapping("category/{categoryId}")
    public List<ProductResponse> findByCategoryId(@PathVariable Integer categoryId) {
        return productService.findByCategoryId(categoryId);
    }

    @GetMapping("supplier/{supplierId}")
    public List<ProductResponse> findBySupplierId(@PathVariable Integer supplierId) {
        return productService.findBySupplierId(supplierId);
    }

    @GetMapping("{productId}/sales/total")
    public ProductBySales findTotalSalesByProductId(@PathVariable Integer productId) {
        return productService.findTotalSalesByProductId(productId);
    }

    @GetMapping("ids")
    public List<ProductResponse> findByIds(@RequestParam(name = "ids") List<Integer> ids) {
        return productService.findByIds(ids);
    }
}
