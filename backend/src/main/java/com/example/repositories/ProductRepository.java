package com.example.repositories;

import com.example.entities.Category;
import com.example.entities.Product;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface ProductRepository extends CrudRepository<Product, Long> {
    @Override
    List<Product> findAll();

    Optional<Product> findProductById(Long id);

    @Query("SELECT * FROM products WHERE id IN (:ids)")
    List<Product> findByMultipleProductIds(@Param("ids") Set<Long> ids);

    List<Product> findProductsByCategoryId(Long id);

    @Query("SELECT * FROM CATEGORIES")
    List<Category> findAllCategories();


    /*
    Methods below return only available products, that is where in-stock value is above zero
     */

    @Query("SELECT * FROM PRODUCTS WHERE CATEGORY_ID = :id AND STOCK > 0")
    List<Product> findAvailableProductByCategoryId(@Param("id") Long id);

    @Query("SELECT * FROM PRODUCTS WHERE STOCK > 0")
    Stream<Product> findAllAvailableProductsStream();

    @Query("SELECT * FROM products WHERE CATEGORY_ID IN (:ids) AND STOCK > 0")
    Stream<Product> findAvailableProductsByMultipleCategoryIds(@Param("ids") Set<Long> ids);


}
