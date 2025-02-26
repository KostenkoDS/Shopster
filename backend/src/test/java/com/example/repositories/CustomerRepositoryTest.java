package com.example.repositories;

import com.example.entities.Customer;
import com.example.shopster.ShopsterApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ShopsterApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @Test
    void findCustomerByEmail(){
        Customer c = repository.findCustomerByUsername("a@g.com").orElseThrow();
        assertAll(
                () -> assertEquals(1L ,c.getId()),
                () -> assertEquals("Rodrigo", c.getName()),
                () -> assertEquals("Fernandes", c.getSurname()),
                () -> assertEquals(1L, c.getUserId())
        );
    }

}