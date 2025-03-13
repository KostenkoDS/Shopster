package com.example.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table("ORDERS")
public class Order {
    @Id
    Long id;
    Long customerId;
    LocalDate orderDate;
    Status status;
    @MappedCollection(idColumn = "ORDER_ID")
    Set<OrderDetails> orderDetails = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void addOrderDetails(Long productId, int amount, BigDecimal price){
        OrderDetails od = new OrderDetails();
        od.setOrderId(id);
        od.setProductId(productId);
        od.setAmount(amount);
        od.setPrice(price);
        orderDetails.add(od);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId()) && Objects.equals(getCustomerId(), order.getCustomerId()) && Objects.equals(getOrderDate(), order.getOrderDate()) && getStatus() == order.getStatus() && Objects.equals(getOrderDetails(), order.getOrderDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomerId(), getOrderDate(), getStatus(), getOrderDetails());
    }

    public enum Status {
        PENDING,READY,PROCESSING,COMPLETED,CANCELED;
    }
}
