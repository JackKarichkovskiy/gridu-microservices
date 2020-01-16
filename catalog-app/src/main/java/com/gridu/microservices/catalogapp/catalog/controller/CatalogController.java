package com.gridu.microservices.catalogapp.catalog.controller;

import com.gridu.microservices.catalogapp.catalog.model.Product;
import com.gridu.microservices.catalogapp.catalog.service.ProductService;
import com.gridu.microservices.catalogapp.catalog.model.ProductsResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class CatalogController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        return productService.findProduct(id);
    }

    @GetMapping
    public ProductsResponse getProductsBySku(@RequestParam String sku) {
        return productService.findProductsWithSku(sku);
    }
}
