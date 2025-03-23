package com.example.services;

import com.example.dto.OrderDTO;
import com.example.dto.OrderDetailsDTO;
import com.example.dto.ProductDTO;
import com.example.entities.Order;
import com.example.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;

    public Set<OrderDTO> getAllOrdersByCustomerId(Long customerId){
        List<Order> orderRecords = orderRepository.findOrdersByCustomerId(customerId);
        Map<Long, String> productNames = productService.findProductNamesFromListOfOrders(orderRecords);
        return orderRecords.stream().map(o -> new OrderDTO(o,productNames)).collect(Collectors.toSet());
    }

    public OrderDTO getCustomerOrderByOrderId(Long customerId, Long orderId){
        Order orderRecord = orderRepository.findOrderByCustomerIdAndOrderId(customerId, orderId).orElseThrow();
        Map<Long, String> productNames = productService.findProductNamesFromListOfOrders(List.of(orderRecord));
        return new OrderDTO(orderRecord, productNames);
    }

    @Transactional
    public OrderDTO createOrder(Long customerId, Set<OrderDetailsDTO> orderDetailsData){
        checkPrices(orderDetailsData);
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderDate(LocalDate.now());
        order.setStatus(Order.Status.PENDING);
        Order savedOrder = orderRepository.save(order);

        orderDetailsData.forEach(od -> order.addOrderDetails(od.getProductId(), od.getAmount(), od.getPrice()));
        savedOrder = orderRepository.save(order);

        Map<Long, String> productNames = productService.findProductNamesFromOrder(savedOrder);
        return new OrderDTO(savedOrder, productNames);
    }

    public OrderDTO findOrderById(Long id){
        Order order = orderRepository.findOrderById(id).orElseThrow();
        Map<Long, String> productNames = productService.findProductNamesFromOrder(order);
        return new OrderDTO(order, productNames);
    }

    public void checkPrices(Set<OrderDetailsDTO> orderDetailsData){
        Set<Long> productIds = orderDetailsData.stream().map(OrderDetailsDTO::getProductId).collect(Collectors.toSet());
        Map<Long, BigDecimal> productPrices = productService.findProductsByIds(productIds)
                .stream()
                .collect(Collectors.toMap(ProductDTO::getId, ProductDTO::getPrice));
        boolean pricesMatch = orderDetailsData.stream().allMatch(od -> od.getPrice().equals(productPrices.get(od.getProductId())));
        if(!pricesMatch)
            throw new PriceMismatchException("Prices in the order do not match those from the database");
    }
}
