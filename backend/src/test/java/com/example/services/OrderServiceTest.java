package com.example.services;

import com.example.dto.OrderDTO;
import com.example.dto.OrderDetailsDTO;
import com.example.entities.Order;
import com.example.shopster.ShopsterApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ShopsterApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
class OrderServiceTest {
    @Autowired
    OrderService service;

    @Test
    void createAndRetrieveOrder() throws Exception {
        /*
        Prepare test data for saving
        Collect data from the order details into separate lists for comparison
        */
        URL orderDataURL = Thread.currentThread().getContextClassLoader().getResource("test-values/customer-orders/order-submission-test-values.json");
        String orderDataJson = Files.readString(Paths.get(orderDataURL.toURI()));
        Set<OrderDetailsDTO> orderData = new ObjectMapper().readValue(orderDataJson, new TypeReference<Set<OrderDetailsDTO>>() {});
        List<Long> productsToSave = orderData.stream().map(OrderDetailsDTO::getProductId).toList();
        List<BigDecimal> pricesToSave = orderData.stream().map(OrderDetailsDTO::getPrice).toList();
        List<Integer> amountToSave = orderData.stream().map(OrderDetailsDTO::getAmount).toList();

        /*
        Create an order
        Collect data from the order details again and compare them
         */
        OrderDTO savedOrderData = service.createOrder(1L, orderData);
        List<Long> productsSaved = savedOrderData.getOrderDetails().stream().map(OrderDetailsDTO::getProductId).toList();
        List<BigDecimal> pricesSaved = savedOrderData.getOrderDetails().stream().map(OrderDetailsDTO::getPrice).toList();
        List<Integer> amountSaved = savedOrderData.getOrderDetails().stream().map(OrderDetailsDTO::getAmount).toList();

        assertAll(
                () -> assertNotNull(savedOrderData.getId()),
                () -> assertEquals(1L, savedOrderData.getCustomerId()),
                () -> assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), savedOrderData.getOrderDate()),
                () -> assertEquals(Order.Status.PENDING, savedOrderData.getStatus()),
                () -> assertThat(productsToSave, containsInAnyOrder(productsSaved.toArray())),
                () -> assertThat(pricesToSave, containsInAnyOrder(pricesSaved.toArray())),
                () -> assertThat(amountToSave, containsInAnyOrder(amountSaved.toArray()))
        );

        //Retrieve the order data and assert that it is the same as before
        OrderDTO retrievedOrderData = service.findOrderById(savedOrderData.getId());
        assertEquals(savedOrderData, retrievedOrderData);
    }

    @Test
    void checkPricesInOrderData() throws Exception{
        URL orderDataURL = Thread.currentThread().getContextClassLoader().getResource("test-values/customer-orders/order-submission-with-wrong-prices.json");
        String orderDataJson = Files.readString(Paths.get(orderDataURL.toURI()));
        Set<OrderDetailsDTO> orderData = new ObjectMapper().readValue(orderDataJson, new TypeReference<Set<OrderDetailsDTO>>() {});

        assertThrows(PriceMismatchException.class, () -> service.checkPrices(orderData));
    }
}