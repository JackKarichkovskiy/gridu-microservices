package com.gridu.microservices.productapp.product.model;

import lombok.Data;
import lombok.Value;

import java.util.Map;

@Data
public class ProductInventory {

    private Map<String, Integer> inventory;
}
