package com.example.controllers;

import com.example.dto.GeneralUserDTO;
import com.example.dto.RegistrationDTO;
import com.example.entities.Customer;
import com.example.services.CustomerService;
import com.example.shopster.ShopsterApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ShopsterApplication.class})
@TestPropertySource(locations="classpath:application-test.properties")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    CustomerService customerService;

    @Test
    void createCustomer() throws Exception {
        URL testValueURL = Thread.currentThread().getContextClassLoader().getResource("test-values/customer-registration-test-value.json");
        String userData = Files.readString(Paths.get(testValueURL.toURI()));
        mockMvc.perform(post("/api/customer")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userData))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/customer")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userData))
                .andExpect(status().isBadRequest());

        URL expectedJsonURL = Thread.currentThread().getContextClassLoader().getResource("test-values/customer-return-test-value.json");
        String expectedJson = Files.readString(Paths.get(expectedJsonURL.toURI()));
        GeneralUserDTO userDTO = new ObjectMapper().readValue(expectedJson, GeneralUserDTO.class);
        mockMvc.perform(get("/api/customer")
                        .with(user("w@g.com").authorities(List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER")))))
                .andExpect(content().json(expectedJson));
    }
}