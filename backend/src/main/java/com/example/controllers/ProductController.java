package com.example.controllers;

import com.example.dto.CategoryDTO;
import com.example.dto.ProductDTO;
import com.example.services.ProductService;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ProductController {
    private ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    //We have 5 product categories
    @GetMapping("/api/products")
    List<ProductDTO> getAllProductsWithQueries(@RequestParam(value = "c", required = false) @Size(min = 1, max = 5) Set<@Positive Long> categories,
                                            @RequestParam(required = false) @Positive Long minPrice,
                                            @RequestParam(required = false) @Positive Long maxPrice,
                                            @RequestParam(required = false) @Pattern(regexp = "^(?!\\s*$)[A-Za-zÀ-ÿ0-9 ]{1,30}$") String name){
        return service.findAvailableProductsWithQueries(categories, minPrice, maxPrice, name);
    }

    @GetMapping("/api/products/list")
    List<ProductDTO> getAllProductsInList(@RequestParam(value = "p") @Size(min = 1) List<@Positive Long> productIds){
        Set<Long> productIdsSet = new HashSet<>(productIds);
        return service.findProductsByIds(productIdsSet);
    }

    @GetMapping("/api/products/{id}")
    ProductDTO getSingleProduct(@PathVariable("id") @Positive Long id) {
        return service.findProductById(id);
    }

    //returns a list of categories - ids and names
    @GetMapping("/api/categories")
    List<CategoryDTO> getcategories(){
        return service.findAllCategories();
    }
}
