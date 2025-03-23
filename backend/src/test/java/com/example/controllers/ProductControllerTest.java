package com.example.controllers;

import com.example.dto.ProductDTO;
import com.example.services.ProductService;
import com.example.shopster.ShopsterApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
        List<ProductDTO> productsByCategory = service.findAvailableProductsByCategoryId(categoryId);
        expectedJson = new ObjectMapper().writeValueAsString(productsByCategory);
        mockMvc.perform(get("/api/products?c=1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void findListOfProducts() throws Exception {
        URL testValueURL = Thread.currentThread().getContextClassLoader().getResource("test-values/products-with-queries.json");
        String expectedJson = Files.readString(Paths.get(testValueURL.toURI()));

        mockMvc.perform(get("/api/products/list?p=2&p=4"))
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

    @ParameterizedTest
    @MethodSource("badValuesForQueries")
    void attemptFindingProductsWithInvalidQueries(String query) throws Exception {
        mockMvc.perform(get(query))
                .andExpect(status().isBadRequest());
    }

    @Test
    void attemptFindingListOfProductsWithInvalidList() throws Exception {
        mockMvc.perform(get("/api/products/list?p=0"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/products/list?p=-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void attemptFindingAProductWithQuery() throws Exception {
        mockMvc.perform(get("/api/products/0"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/products/-1"))
                .andExpect(status().isBadRequest());
    }

    private List<String> badValuesForQueries(){
        return List.of(
                new QueryValuesWrapper(Set.of(), 0L, 0L, "a").toQuery(),
                new QueryValuesWrapper(Set.of(-1L), 1L, 1L, "a").toQuery(),
                new QueryValuesWrapper(Set.of(1L), -1L, 1L, "a").toQuery(),
                new QueryValuesWrapper(Set.of(1L), 1L, -1L, "a").toQuery(),
                new QueryValuesWrapper(Set.of(1L), 1L, 1L, "").toQuery(),
                new QueryValuesWrapper(Set.of(1L), 1L, 1L, " ").toQuery(),
                new QueryValuesWrapper(Set.of(1L), 1L, 1L, "+").toQuery(),
                new QueryValuesWrapper(null, 1L, 1L, "alksvnpasinvoaisnvlkqnwoivqnoviknqlsnaoivboanlaknoiqbasaaacs").toQuery(),
                new QueryValuesWrapper(null, 1L, 1L, "a+").toQuery()
        );
    }

    private static class QueryValuesWrapper {
        final Set<Long> categories;
        final Long minPrice;
        final Long maxPrice;
        final String name;

        QueryValuesWrapper(Set<Long> categories, Long minPrice, Long maxPrice, String name) {
            this.categories = categories;
            this.minPrice = minPrice;
            this.maxPrice = maxPrice;
            this.name = name;
        }

        String toQuery(){
            String url = "/api/products?";

            StringBuilder categoriesQueryBuilder = new StringBuilder();
            if(categories != null)
                categories.forEach(c -> categoriesQueryBuilder.append("c=").append(c).append("&"));
            String categoriesQuery = categoriesQueryBuilder.toString();
            if(!categoriesQuery.isEmpty())
                categoriesQuery = categoriesQuery.substring(0, categoriesQuery.length()-1);

            String minPriceQuery = (minPrice == null) ? "" : "minPrice="+minPrice;

            String maxPriceQuery = (maxPrice == null) ? "" : "maxPrice="+maxPrice;

            String nameQuery = (name == null) ? "" : "name="+name;

            String resultingString;
            if(categories != null && minPriceQuery.isEmpty() && maxPriceQuery.isEmpty()){
                resultingString = categoriesQuery;
            }
            else resultingString = categoriesQuery+"&"+minPriceQuery+"&"+maxPriceQuery+"&"+nameQuery;
            if(resultingString.startsWith("&"))
                resultingString = resultingString.substring(1);
            return url+resultingString;
        }
    }
}