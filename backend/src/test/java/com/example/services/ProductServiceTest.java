package com.example.services;

import com.example.dto.ProductDTO;
import com.example.entities.Product;
import com.example.shopster.ShopsterApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ShopsterApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
class ProductServiceTest {

    @Autowired
    ProductService service;

    @Test
    void findProductsUsingDifferentQueries() {
        List<String> categories = List.of("1","2","3");
        List<Long> categoriesLong = categories.stream().map(Long::valueOf).toList();
        String minPrice = "1";
        String maxPrice = "1000";
        String name = "amd";
        System.out.println(categoriesLong);
        List<ProductDTO> products = service.findInAllProductsWithQueries(categories, minPrice, maxPrice, name);
        assertTrue(products.stream().allMatch(
                p -> (categoriesLong.contains(p.getCategoryId())
                    && (p.getPrice().compareTo(new BigDecimal(minPrice)) >=0)
                    && (p.getPrice().compareTo(new BigDecimal(maxPrice)) <=0)
                    && p.getName().toLowerCase().contains(name.toLowerCase())))
        );
    }
}