package com.example.orderservice.service;

import com.example.orderservice.dto.InventoryResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repository;
    private final WebClient inventoryWebClient;


    public List<Order> findAll() {
        return (List<Order>) repository.findAll();
    }

    public Order findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Order createOrder(Order order) {
        // Reactive call to inventory, block to keep method synchronous
        InventoryResponse inv = inventoryWebClient.get()
                .uri("/inventory/{sku}", order.getProductSku())
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .block();
        if (inv == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found");
        }
        if (inv.quantityAvailable() < order.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough inventory");
        }
        order.setStatus("CREATED");
        return repository.save(order);
    }

    public Order update(UUID id, Order updated) {
        Order existing = repository.findById(id).orElse(null);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        existing.setProductSku(updated.getProductSku());
        existing.setQuantity(updated.getQuantity());
        existing.setPrice(updated.getPrice());
        existing.setStatus(updated.getStatus());
        return repository.save(existing);
    }

    public void deleteOrder(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        repository.deleteById(id);
    }
}