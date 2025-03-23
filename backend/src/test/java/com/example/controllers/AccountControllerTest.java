package com.example.controllers;

import com.example.dto.GeneralUserDTO;
import com.example.dto.RegistrationDTO;
import com.example.services.CustomerService;
import com.example.shopster.ShopsterApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @ParameterizedTest
    @MethodSource("validUserDataValues")
    void createCustomerGetAndDelete(RegistrationDTO userData) throws Exception {
        String userDataJson = new ObjectMapper().writeValueAsString(userData);
        mockMvc.perform(post("/api/customer")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJson))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/customer")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJson))
                .andExpect(status().isBadRequest());

        //Removes the password string from JSON
        String expectedJson = userDataJson.replaceFirst("(?<=\\.[a-z]{3}\")(.*)(?=\"name\")", ",");
        mockMvc.perform(get("/api/customer")
                        .with(user(userData.getEmail()).authorities(List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER")))))
                .andExpect(content().json(expectedJson));

        customerService.deleteCustomerByEmail(userData.getEmail());
        assertThrows(UsernameNotFoundException.class, () -> customerService.findCustomerByEmail(userData.getEmail()));
    }

    @ParameterizedTest
    @MethodSource("invalidUserDataValues")
    void invalidSignUpValuesTest(RegistrationDTO invalidUserData) throws Exception {
        String userDataJson = new ObjectMapper().writeValueAsString(invalidUserData);
        mockMvc.perform(post("/api/customer")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataJson))
                .andExpect(status().isBadRequest());
    }

    private List<RegistrationDTO> validUserDataValues() throws URISyntaxException, IOException {
        URL testValueURL = Thread.currentThread().getContextClassLoader().getResource("test-values/customer-sign-up/valid-sign-up-data-values.json");
        String userData = Files.readString(Paths.get(testValueURL.toURI()));
        return new ObjectMapper().readValue(userData, new TypeReference<List<RegistrationDTO>>() {});
    }

    private List<RegistrationDTO> invalidUserDataValues() throws URISyntaxException, IOException {
        URL testValueURL = Thread.currentThread().getContextClassLoader().getResource("test-values/customer-sign-up/invalid-sign-up-data-values.json");
        String userData = Files.readString(Paths.get(testValueURL.toURI()));
        return new ObjectMapper().readValue(userData, new TypeReference<List<RegistrationDTO>>() {});
    }
}