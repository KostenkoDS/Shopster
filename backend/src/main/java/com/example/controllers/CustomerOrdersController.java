package com.example.controllers;

import com.example.dto.OrderDTO;
import com.example.dto.OrderDetailsDTO;
import com.example.services.CustomerService;
import com.example.services.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class CustomerOrdersController {

    @Autowired
    CustomerService customerService;
    @Autowired
    OrderService orderService;

    @GetMapping("/api/customer/orders")
    Set<OrderDTO> getCustomerOrders(Authentication auth){
        Long customerId = customerService.findCustomerIdByEmail(auth.getName());
        Set<OrderDTO> orders = orderService.getAllOrdersByCustomerId(customerId);
        return orders;
    }

    @GetMapping("/api/customer/orders/{orderNumber}")
    OrderDTO getCustomerOrderByNumber(@PathVariable("orderNumber")
                                      @NotNull(message = "The order number is missing (null)")
                                      Long orderNumber,
                                      Authentication auth){
        Long customerId = customerService.findCustomerIdByEmail(auth.getName());
        return orderService.getCustomerOrderByOrderId(customerId, orderNumber);
    }

    @PostMapping("/api/customer/orders")
    @ResponseStatus(HttpStatus.CREATED)
    OrderDTO createOrder(@RequestBody
                         @Size(min = 1, max = 100, message = "Unacceptable amount of order details")
                         Set<@Valid OrderDetailsDTO> orderData,
                         Authentication auth){
        Long customerId = customerService.findCustomerIdByEmail(auth.getName());
        return orderService.createOrder(customerId, orderData);
    }

}
