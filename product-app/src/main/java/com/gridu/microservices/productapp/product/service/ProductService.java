package com.gridu.microservices.productapp.product.service;

import com.gridu.microservices.productapp.product.exception.NotFoundException;
import com.gridu.microservices.productapp.product.exception.ServiceUnavailableException;
import com.gridu.microservices.productapp.product.model.Product;
import com.gridu.microservices.productapp.product.model.ProductInventory;
import com.gridu.microservices.productapp.product.model.Products;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "throwErrorDuringGetProductFallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }
    )
    public Optional<Product> getProductById(String id) {
        log.info("In Product Service");
        ResponseEntity<Product> productResponseEntity = restTemplate.getForEntity("http://catalog-service/products/{id}",
                Product.class, id);
        if (productResponseEntity.getStatusCode() == HttpStatus.OK) {
            ResponseEntity<ProductInventory> inventoryResponseEntity = restTemplate.getForEntity("http://inventory-service/inventories?ids={ids}",
                    ProductInventory.class, id);
            if (inventoryResponseEntity.getStatusCode() == HttpStatus.OK
                    && inventoryResponseEntity.getBody().getInventory().get(id) > 0) {
                return Optional.of(productResponseEntity.getBody());
            } else if (inventoryResponseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NotFoundException("Product not available: " + id);
            }
        } else if (productResponseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new NotFoundException("Product not found: " + id);
        }

        return Optional.empty();
    }

    @HystrixCommand(fallbackMethod = "throwErrorDuringGetProductsBySkuFallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }
    )
    public Products getProductsBySku(String sku) {
        log.info("In Product Service");
        ResponseEntity<Products> productsResponseEntity = restTemplate.getForEntity("http://catalog-service/products?sku={sku}",
                Products.class, sku);
        if (productsResponseEntity.getStatusCode() == HttpStatus.OK) {
            Products products = productsResponseEntity.getBody();
            List<String> productIds = products.getProducts().stream().map(Product::getId).collect(Collectors.toList());
            ResponseEntity<ProductInventory> inventoryResponseEntity = restTemplate.getForEntity("http://inventory-service/inventories?ids={ids}",
                    ProductInventory.class, String.join(",", productIds));
            if (inventoryResponseEntity.getStatusCode() == HttpStatus.OK) {
                ProductInventory inventory = inventoryResponseEntity.getBody();
                List<Product> availableProducts = products.getProducts().stream()
                        .filter(product -> inventory.getInventory().get(product.getId()) > 0)
                        .collect(Collectors.toList());
                return new Products(availableProducts);
            }
        }

        return new Products();
    }

    @SuppressWarnings("unused")
    Optional<Product> throwErrorDuringGetProductFallback(String str) {
        throw new ServiceUnavailableException("Service unavailable");
    }

    @SuppressWarnings("unused")
    Products throwErrorDuringGetProductsBySkuFallback(String str) {
        throw new ServiceUnavailableException("Service unavailable");
    }
}
