package com.example.repositories;

import com.example.entities.Product;
import com.example.shopster.ShopsterApplication;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.relational.core.query.CriteriaStepExtensionsKt.isIn;

@SpringBootTest(classes = ShopsterApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void createAndThenFindProduct(){
        Product product = getTestProduct();
        Product savedProduct = productRepository.save(product);
        savedProduct.addPictureUrl(1, "A");
        productRepository.save(savedProduct);

        List<Product> foundProducts = productRepository.findAll();

        List<Product> foundProductsByCategory = productRepository.findProductsByCategoryId(1L);
        Product[] foundProductsByCategoryAsArray = foundProductsByCategory.toArray(new Product[0]);
        assertThat(foundProducts, hasItems(foundProductsByCategoryAsArray));
        assertThat(foundProducts, hasItem(savedProduct));
    }

    private Product getTestProduct(){
        Product p = new Product();
        p.setName("GTX 666");
        p.setCategoryId(1L);
        p.setPrice(new BigDecimal("420.69"));
        p.setStock(1);
        p.setStockMin(1);
        p.setStockMax(1);
        p.setDescription("uber");
        return p;
    }
}