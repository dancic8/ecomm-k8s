package com.example.orderservice.repository;


import com.example.orderservice.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {

    List<Order> findByStatus(String status);

}