package com.gridu.microservices.inventoryapp.inventory.model;

import lombok.Value;

import java.util.Map;

@Value(staticConstructor = "of")
public class InventoryResponse {

    Map<String, Integer> inventory;
}
