package com.example.dto;

import com.example.entities.Product;
import com.example.entities.ProductPicturesURLs;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        productPictures = productEntity.getProductPictures().stream().collect(Collectors.toMap(ProductPicturesURLs::getSequence, ProductPicturesURLs::getUrl));
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getCategoryId(), that.getCategoryId()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCategoryId(), getPrice(), getDescription());
    }
}