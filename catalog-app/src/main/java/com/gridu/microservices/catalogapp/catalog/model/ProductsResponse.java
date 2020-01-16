package com.gridu.microservices.catalogapp.catalog.model;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class ProductsResponse {

    List<Product> products;
}
