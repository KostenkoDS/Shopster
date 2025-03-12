package com.example.repositories;

import com.example.entities.Order;
import com.example.shopster.ShopsterApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ShopsterApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
class OrderRepositoryTest {

    @Autowired
    OrderRepository repository;

    @Test
    void createAndFindOrder(){
        Order o = getTestOrder();
        Order savedOrder = repository.save(o);
        savedOrder.addOrderDetails(1L, 1, new BigDecimal("100.00"));
        repository.save(savedOrder);

        Order retrievedOrderById = repository.findOrderById(1L).orElseThrow();
        assertEquals(savedOrder, retrievedOrderById);

        List<Order> retrievedOrderByCustomerId = repository.findOrdersByCustomerId(1L);
        assertEquals(savedOrder, retrievedOrderByCustomerId.getFirst());
    }

    private Order getTestOrder(){
        Order o = new Order();
        o.setCustomerId(1L);
        o.setOrderDate(LocalDate.of(2025, Month.FEBRUARY, 25));
        o.setStatus(Order.Status.PENDING);
        return o;
    }
}