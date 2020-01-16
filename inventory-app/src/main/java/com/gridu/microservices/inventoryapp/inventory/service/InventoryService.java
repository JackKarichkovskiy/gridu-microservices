package com.gridu.microservices.inventoryapp.inventory.service;

import com.gridu.microservices.inventoryapp.inventory.model.InventoryResponse;
import com.gridu.microservices.inventoryapp.inventory.model.ProductInventory;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryService {

    private final ProductInventory productInventory;

    public InventoryResponse getInventoryByIds(List<String> ids) {
        log.info("In Inventory Service");
        Map<String, Integer> inventory = productInventory.getInventory();
        @Value
        class Pair<L, R> {
            L left;
            R right;
        }
        Map<String, Integer> inventoryMap = ids.stream()
                .filter(inventory::containsKey)
                .map(id -> new Pair<>(id, inventory.get(id)))
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
        return InventoryResponse.of(inventoryMap);
    }
}
