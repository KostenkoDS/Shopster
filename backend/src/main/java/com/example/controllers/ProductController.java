package com.example.controllers;

import com.example.entities.Product;
import com.example.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    private ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    List<Product> getAllProductsWithQueries(@RequestParam(value = "c", required = false) List<String> categories,
                                            @RequestParam(required = false) String minPrice,
                                            @RequestParam(required = false) String maxPrice,
                                            @RequestParam(required = false) String name){
        return service.findInAllProductsWithQueries(categories, minPrice, maxPrice, name);
    }

    @GetMapping("/products/{id}")
    Product getSingleProduct(@PathVariable("id") Long id) {
        return service.findProductById(id);
    }
}
