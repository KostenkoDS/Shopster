package com.example.controllers;

import com.example.dto.GeneralUserDTO;
import com.example.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/api/customer")
    GeneralUserDTO getCustomer(Authentication auth){
        return customerService.findCustomerByEmail(auth.getName());
    }
}
