package com.gridu.microservices.inventoryapp.inventory.controller;

import com.gridu.microservices.inventoryapp.inventory.model.InventoryResponse;
import com.gridu.microservices.inventoryapp.inventory.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inventories")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public InventoryResponse getInventoryCounts(@RequestParam List<String> ids) {
        return inventoryService.getInventoryByIds(ids);
    }
}
