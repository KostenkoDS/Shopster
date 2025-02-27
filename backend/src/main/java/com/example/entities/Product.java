package com.example.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.*;

@Table("PRODUCTS")
public class Product {
    @Id
    Long id;
    String name;
    Long categoryId;
    BigDecimal price;
    Integer stock;
    Integer stockMin;
    Integer stockMax;
    String description;
    @MappedCollection(idColumn = "PRODUCT_ID")
    Set<ProductPicturesURLs> productPictures = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStockMin() {
        return stockMin;
    }

    public void setStockMin(Integer stockMin) {
        this.stockMin = stockMin;
    }

    public Integer getStockMax() {
        return stockMax;
    }

    public void setStockMax(Integer stockMax) {
        this.stockMax = stockMax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addPictureUrl(int sequence, String url){
        ProductPicturesURLs p = new ProductPicturesURLs();
        p.setProductId(id);
        p.setSequence(sequence);
        p.setUrl(url);
        productPictures.add(p);
    }

    public Set<ProductPicturesURLs> getProductPictures() {
        return productPictures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId()) && Objects.equals(getName(), product.getName()) && Objects.equals(getCategoryId(), product.getCategoryId()) && Objects.equals(getPrice(), product.getPrice()) && Objects.equals(getStock(), product.getStock()) && Objects.equals(getStockMin(), product.getStockMin()) && Objects.equals(getStockMax(), product.getStockMax()) && Objects.equals(getDescription(), product.getDescription()) && Objects.equals(getProductPictures(), product.getProductPictures());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCategoryId(), getPrice(), getStock(), getStockMin(), getStockMax(), getDescription(), getProductPictures());
    }
}
