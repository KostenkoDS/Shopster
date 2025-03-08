package com.example.repositories;

import com.example.entities.Category;
import com.example.entities.Product;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ProductRepository extends CrudRepository<Product, Long> {
    @Override
    List<Product> findAll();
    Optional<Product> findProductById(Long id);
    List<Product> findProductsByCategoryId(Long id);
    @Query("SELECT * FROM PRODUCTS WHERE STOCK > 0")
    Stream<Product> findAllAvailableProductsStream();
    @Query("SELECT * FROM products WHERE category_id IN (:ids)")
    Stream<Product> findByMultipleIds(@Param("ids") List<Long> ids);
    @Query("SELECT * FROM CATEGORIES")
    List<Category> findAllCategories();
}
