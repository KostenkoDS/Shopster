package com.example.dto;

import com.example.entities.OrderDetails;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderDetailsDTO {
    final Long productId;
    final String productName;
    final Integer amount;
    final BigDecimal price;

    public OrderDetailsDTO(@JsonProperty("productId")
                           Long productId,
                           @JsonProperty("productName")
                           String productName,
                           @JsonProperty("amount")
                           Integer amount,
                           @JsonProperty("price")
                           BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.amount = amount;
        this.price = price;
    }

    public OrderDetailsDTO(OrderDetails orderDetails, String productName) {
        this.productId = orderDetails.getProductId();
        this.productName = productName;
        this.amount = orderDetails.getAmount();
        this.price = orderDetails.getPrice();
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsDTO that = (OrderDetailsDTO) o;
        return Objects.equals(getProductId(), that.getProductId()) && Objects.equals(productName, that.productName) && Objects.equals(getAmount(), that.getAmount()) && Objects.equals(getPrice(), that.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), productName, getAmount(), getPrice());
    }
}
