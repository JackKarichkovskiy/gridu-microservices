package com.gridu.microservices.catalogapp.catalog.service;

import com.gridu.microservices.catalogapp.catalog.repository.ProductRepository;
import com.gridu.microservices.catalogapp.catalog.exception.NotFoundException;
import com.gridu.microservices.catalogapp.catalog.model.Product;
import com.gridu.microservices.catalogapp.catalog.model.ProductsResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public Product findProduct(String id) {
        log.info("In Catalog Service");
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product wasn't found: " + id));
    }

    public ProductsResponse findProductsWithSku(String sku) {
        return ProductsResponse.of(productRepository.findAllBySku(sku));
    }
}
