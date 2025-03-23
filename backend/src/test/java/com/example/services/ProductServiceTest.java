package com.example.services;

import com.example.dto.ProductDTO;
import com.example.shopster.ShopsterApplication;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = ShopsterApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
class ProductServiceTest {

    @Autowired
    ProductService service;

    @ParameterizedTest
    @MethodSource("goodValuesForQueries")
    void findProductsUsingDifferentQueries(QueryValuesWrapper values) {
        Set<Long> categories = values.categories;
        Long minPrice = values.minPrice;
        Long maxPrice = values.maxPrice;
        String name = values.name;
        List<ProductDTO> products = service.findAvailableProductsWithQueries(categories, minPrice, maxPrice, name);

        List<ProductDTO> allProducts = service.findAvailableProducts();
        if(values.allNull()){
            assertThat(products, containsInAnyOrder(allProducts.toArray()));
        }

        List<ProductDTO> categoryProducts = service.findAvailableProductsByCategoryIds(categories);
        if(values.categoriesOnly()){
            assertThat(products, containsInAnyOrder(categoryProducts.toArray()));
        }

        if(categories != null){
            assertTrue(products.stream().allMatch(
                    p -> (categories.contains(p.getCategoryId()))));
        }

        if(minPrice != null){
            assertTrue(products.stream().allMatch(
                    p -> (p.getPrice().compareTo(new BigDecimal(minPrice)) >=0)));
        }

        if(maxPrice != null){
            assertTrue(products.stream().allMatch(
                    p -> (p.getPrice().compareTo(new BigDecimal(maxPrice)) <=0)));
        }

        if(name != null){
            assertTrue(products.stream().allMatch(
                    p -> (p.getName().toLowerCase().contains(name.toLowerCase()))));
        }
    }



    private List<QueryValuesWrapper> goodValuesForQueries(){
        return List.of(
                new QueryValuesWrapper(null, null, null, null),
                new QueryValuesWrapper(Set.of(1L), null, null, null),
                new QueryValuesWrapper(Set.of(1L, 2L), 1L, null, null),
                new QueryValuesWrapper(Set.of(1L, 2L), 1L, 100L, null),
                new QueryValuesWrapper(Set.of(1L, 2L), 1L, 100L, "amd"),
                new QueryValuesWrapper(Set.of(1L, 2L), 1L, 100L, "aMD"),
                new QueryValuesWrapper(Set.of(1L, 2L), 1L, 100L, "AMD"),
                new QueryValuesWrapper(Set.of(1L, 2L), 1L, 19999L, "AMD R")
        );
    }

    private List<QueryValuesWrapper> badValuesForQueries(){
        return List.of(
                new QueryValuesWrapper(Set.of(), 0L, 0L, ""),
                new QueryValuesWrapper(Set.of(-1L), 1L, 1L, "a"),
                new QueryValuesWrapper(null, -1L, -1L, " "),
                new QueryValuesWrapper(null, 1L, 1L, "alksvnpasinvoaisnvlkqnwoivqnoviknqlsnaoivboanlaknoiqbasaaacs"),
                new QueryValuesWrapper(null, 1L, 1L, "a+")
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

        boolean allNull(){
            return (categories ==null) &&
                    (minPrice == null) &&
                    (maxPrice == null) &&
                    (name == null);
        }

        boolean categoriesOnly(){
            return (categories != null) &&
                    (minPrice == null) &&
                    (maxPrice == null) &&
                    (name == null);
        }
    }
}