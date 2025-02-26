package com.example.repositories;

import com.example.entities.Product;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ProductRepository extends CrudRepository<Product, Long> {
    @Override
    List<Product> findAll();
    Optional<Product> findProductById(Long id);
    List<Product> findProductsByCategoryId(Long id);
    @Query("SELECT * FROM PRODUCTS")
    Stream<Product> findAllProductsStream();
}
