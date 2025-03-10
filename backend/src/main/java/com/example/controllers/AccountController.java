package com.example.controllers;

import com.example.dto.GeneralUserDTO;
import com.example.dto.RegistrationDTO;
import com.example.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/api/customer")
    GeneralUserDTO getCustomer(Authentication auth){
        GeneralUserDTO u = customerService.findCustomerByEmail(auth.getName());
        return u;
    }

    @PostMapping("/api/customer")
    @ResponseStatus(HttpStatus.CREATED)
    void createCustomer(@RequestBody RegistrationDTO data){
        customerService.createCustomer(data);
    }
}
