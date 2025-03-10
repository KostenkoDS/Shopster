package com.example.dto;

import com.example.auth.ShopsterUser;
import com.example.entities.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.userdetails.UserDetails;

public class RegistrationDTO {
    String email;
    String password;
    String name;
    String surname;
    String middleName;
    String address;
    String phoneNumber;

    public RegistrationDTO(@JsonProperty("email")
                           String email,
                           @JsonProperty("password")
                           String password,
                           @JsonProperty("name")
                           String name,
                           @JsonProperty("surname")
                           String surname,
                           @JsonProperty("middleName")
                           String middleName,
                           @JsonProperty("address")
                           String address,
                           @JsonProperty("phoneNumber")
                           String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public UserDetails getUserDetails(){
        return new ShopsterUser(email, password, "CUSTOMER");
    }

    public Customer getCustomer(Long userId){
        Customer c = new Customer();
        c.setUserId(userId);
        c.setName(name);
        c.setSurname(surname);
        c.setMiddleName(middleName);
        c.setAddress(address);
        c.setPhoneNumber(phoneNumber);
        return c;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
