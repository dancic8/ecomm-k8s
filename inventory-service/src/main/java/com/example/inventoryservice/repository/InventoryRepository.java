package com.example.inventoryservice.repository;

import com.example.inventoryservice.document.InventoryItem;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;



public interface InventoryRepository extends ReactiveMongoRepository<InventoryItem, String> {

    Mono<InventoryItem> findByProductSku(String productSku);

}