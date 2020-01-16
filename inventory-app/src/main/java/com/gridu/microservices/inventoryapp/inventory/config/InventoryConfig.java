package com.gridu.microservices.inventoryapp.inventory.config;

import com.gridu.microservices.inventoryapp.inventory.model.ProductInventory;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Configuration
public class InventoryConfig {

    private final Random ran = new Random(1L);

    @Bean
    @SneakyThrows
    ProductInventory productInventory() {
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/ykarychkovskyi/dev/GridU Microservices Course/products.csv"))) {
            Map<String, Integer> productInventory = br.lines().skip(1)
                    .map(line -> line.split(","))
                    .collect(Collectors.toMap(csvRow -> csvRow[0], csvRow -> ran.nextInt(2)));
            return ProductInventory.of(productInventory);
        }
    }
}
