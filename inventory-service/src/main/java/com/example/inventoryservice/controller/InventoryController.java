package com.example.inventoryservice.controller;

import com.example.inventoryservice.document.InventoryItem;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryRepository repository;

    @GetMapping("/healthcheck")
    public String health(){
        return "Workss";
    }
    @GetMapping("/{sku}")
    public Mono<InventoryItem> getStock(@PathVariable("sku") String sku) {
        return repository.findByProductSku(sku);
    }

    @PostMapping
    public Mono<InventoryItem> addStock(@RequestBody InventoryItem item) {
        return repository.save(item);
    }
}