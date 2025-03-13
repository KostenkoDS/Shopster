package com.example.dto;

import com.example.entities.Order;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderDTO {
    Long id;
    Long customerId;
    String orderDate;
    Order.Status status;
    Set<OrderDetailsDTO> orderDetails;

    public OrderDTO(@JsonProperty("id")
                    Long id,
                    @JsonProperty("customerId")
                    Long customerId,
                    @JsonProperty("orderDate")
                    String orderDate,
                    @JsonProperty("status")
                    Order.Status status,
                    @JsonProperty("orderDetails")
                    Set<OrderDetailsDTO> orderDetails) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.status = status;
        this.orderDetails = orderDetails;
    }

    public OrderDTO(Order orderRecord, Map<Long, String> productNames){
        this.id = orderRecord.getId();
        this.customerId = orderRecord.getCustomerId();
        this.orderDate = orderRecord.getOrderDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.status = orderRecord.getStatus();
        this.orderDetails = orderRecord.getOrderDetails().stream().map(od -> new OrderDetailsDTO(od, productNames.get(od.getProductId()))).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public Order.Status getStatus() {
        return status;
    }

    public Set<OrderDetailsDTO> getOrderDetails() {
        return orderDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(getId(), orderDTO.getId()) && Objects.equals(getCustomerId(), orderDTO.getCustomerId()) && Objects.equals(getOrderDate(), orderDTO.getOrderDate()) && getStatus() == orderDTO.getStatus() && Objects.equals(getOrderDetails(), orderDTO.getOrderDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomerId(), getOrderDate(), getStatus(), getOrderDetails());
    }
}
