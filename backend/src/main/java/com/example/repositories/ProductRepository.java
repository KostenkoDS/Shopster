package com.example.repositories;

import com.example.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {
    @Override
    List<Product> findAll();
    Optional<Product> findProductById(Long id);
}
