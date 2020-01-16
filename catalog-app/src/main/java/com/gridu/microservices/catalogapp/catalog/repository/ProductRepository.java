package com.gridu.microservices.catalogapp.catalog.repository;

import com.gridu.microservices.catalogapp.catalog.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {

    List<Product> findAllBySku(String sku);
}
