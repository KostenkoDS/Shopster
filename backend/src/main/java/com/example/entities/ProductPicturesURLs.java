package com.example.entities;

import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("PRODUCT_PICTURES")
public class ProductPicturesURLs {
    Long productId;
    Integer sequence;
    String url;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPicturesURLs that = (ProductPicturesURLs) o;
        return Objects.equals(getProductId(), that.getProductId()) && Objects.equals(getSequence(), that.getSequence()) && Objects.equals(getUrl(), that.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getSequence(), getUrl());
    }
}
