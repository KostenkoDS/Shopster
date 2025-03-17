package com.example.controllers;

import com.example.dto.CategoryDTO;
import com.example.dto.ProductDTO;
import com.example.entities.Category;
import com.example.entities.Product;
import com.example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/api/products")
    List<ProductDTO> getAllProductsWithQueries(@RequestParam(value = "c", required = false) List<String> categories,
                                            @RequestParam(required = false) String minPrice,
                                            @RequestParam(required = false) String maxPrice,
                                            @RequestParam(required = false) String name){
        return service.findInAllProductsWithQueries(categories, minPrice, maxPrice, name);
    }

    @GetMapping("/api/products/list")
    List<ProductDTO> getAllProductsWithQueries(@RequestParam(value = "p") List<Long> productIds){
        return service.findProductsByIds(productIds);
    }

    @GetMapping("/api/products/{id}")
    ProductDTO getSingleProduct(@PathVariable("id") Long id) {
        return service.findProductById(id);
    }

    //returns a list of categories - ids and names
    @GetMapping("/api/categories")
    List<CategoryDTO> getcategories(){
        return service.findAllCategories();
    }
}
