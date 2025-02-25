package com.example.repositories;

import com.example.entities.Manager;
import com.example.shopster.ShopsterApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ShopsterApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
class ManagerRepositoryTest {

    @Autowired
    ManagerRepository repository;

    @Test
    void findCustomerByUsername() {
        Manager m = repository.findManagerByUsername("b@g.com").orElseThrow();
        assertAll(
                () -> assertEquals(1L, m.getId()),
                () -> assertEquals("Muhammad", m.getName()),
                () -> assertEquals("Avdol", m.getSurname()),
                () -> assertEquals(2L, m.getUserId())
        );
    }
}