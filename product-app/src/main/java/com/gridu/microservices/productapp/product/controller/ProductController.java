package com.gridu.microservices.productapp.product.controller;

import com.gridu.microservices.productapp.product.service.ProductService;
import com.gridu.microservices.productapp.product.exception.NotFoundException;
import com.gridu.microservices.productapp.product.model.Product;
import com.gridu.microservices.productapp.product.model.Products;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        return productService.getProductById(id)
                .orElseThrow(() -> new NotFoundException("Cannot retrieve product: " + id));
    }

    @GetMapping
    public Products getProductsBySku(@RequestParam String sku) {
        return productService.getProductsBySku(sku);
    }
}
