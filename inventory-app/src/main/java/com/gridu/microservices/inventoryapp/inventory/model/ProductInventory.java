package com.gridu.microservices.inventoryapp.inventory.model;

import lombok.Value;

import java.util.Map;

@Value(staticConstructor = "of")
public class ProductInventory {

    Map<String, Integer> inventory;
}
