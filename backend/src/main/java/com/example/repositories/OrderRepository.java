package com.example.repositories;

import com.example.entities.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findOrderById(Long id);
    @Override
    List<Order> findAll();
    List<Order> findOrdersByCustomerId(Long customerId);
}
