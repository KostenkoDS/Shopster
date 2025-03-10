package com.example.services;

import com.example.auth.ShopsterUserDetailsService;
import com.example.dto.GeneralUserDTO;
import com.example.dto.RegistrationDTO;
import com.example.entities.Customer;
import com.example.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    @Autowired
    ShopsterUserDetailsService userDetailsManager;
    @Autowired
    CustomerRepository customerRepository;

    public GeneralUserDTO findCustomerByEmail(String email){
        Customer customer = customerRepository.findCustomerByUsername(email).orElseThrow();
        return new GeneralUserDTO(customer, email);
    }

    @Transactional
    public void createCustomer(RegistrationDTO userData){
        userDetailsManager.createUser(userData.getUserDetails());
        Long userId = userDetailsManager.getUserIdByUsername(userData.getEmail());
        customerRepository.save(userData.getCustomer(userId));
    }
}
