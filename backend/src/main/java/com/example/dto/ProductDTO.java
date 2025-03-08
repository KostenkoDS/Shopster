package com.example.dto;

import com.example.entities.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProductDTO {
    Long id;
    String name;
    Long categoryId;
    BigDecimal price;
    String description;

    Map<Integer, String> productPictures;

    public ProductDTO(Product productEntity) {
        id = productEntity.getId();
        name = productEntity.getName();
        categoryId = productEntity.getCategoryId();
        price = productEntity.getPrice();
        description = productEntity.getDescription();
        productPictures = new HashMap<>();
        productEntity.getProductPictures().forEach(p -> productPictures.put(p.getSequence(), p.getUrl()));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Map<Integer, String> getProductPictures() {
        return productPictures;
    }
}

