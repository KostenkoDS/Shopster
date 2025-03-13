package com.example.controllers;

import com.example.shopster.ShopsterApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ShopsterApplication.class})
@TestPropertySource(locations="classpath:application-test.properties")
@AutoConfigureMockMvc
class CustomerOrdersControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithUserDetails("a@g.com")
    void postAnOrderAndGetIt() throws Exception {
        URL testValueURL = Thread.currentThread().getContextClassLoader().getResource("test-values/order-submission-request-data-test-values.json");
        String orderData = Files.readString(Paths.get(testValueURL.toURI()));

        URL expectedValueURL = Thread.currentThread().getContextClassLoader().getResource("test-values/created-order-response-test-value.json");
        String expectedJson = Files.readString(Paths.get(expectedValueURL.toURI()));

        mockMvc.perform(post("/api/customer/orders")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderData))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));

        mockMvc.perform(get("/api/customer/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        expectedJson = "["+expectedJson+"]";
        mockMvc.perform(get("/api/customer/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}