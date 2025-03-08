package com.example.controllers;

import com.example.config.WebSecurityConfiguration;
import com.example.dto.ProductDTO;
import com.example.entities.Product;
import com.example.services.ProductService;
import com.example.shopster.ShopsterApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ShopsterApplication.class})
@TestPropertySource(locations="classpath:application-test.properties")
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProductService service;

    @Test
    void findProductsWithQueries() throws Exception {
        URL testValueURL = Thread.currentThread().getContextClassLoader().getResource("test-values/products-with-queries.json");
        String expectedJson = Files.readString(Paths.get(testValueURL.toURI()));

        mockMvc.perform(get("/api/products?c=1&c=2&c=3&minPrice=1&maxPrice=1000&name=amd"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        List<ProductDTO> allProducts = service.findAvailableProducts();
        expectedJson = new ObjectMapper().writeValueAsString(allProducts);
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        Long categoryId = 1L;
        List<ProductDTO> productsByCategory = service.findProductsByCategoryId(categoryId);
        expectedJson = new ObjectMapper().writeValueAsString(productsByCategory);
        mockMvc.perform(get("/api/products?c=1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void findSingleProduct() throws Exception {
        URL testValueURL = Thread.currentThread().getContextClassLoader().getResource("test-values/first-test-product.json");
        String expectedJson = Files.readString(Paths.get(testValueURL.toURI()));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void findAllCategories() throws Exception {
        URL testValueURL = Thread.currentThread().getContextClassLoader().getResource("test-values/categories.json");
        String expectedJson = Files.readString(Paths.get(testValueURL.toURI()));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}