package com.example.dto;

import com.example.auth.ShopsterUser;
import com.example.entities.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.core.userdetails.UserDetails;

public class RegistrationDTO {
    @NotNull
    @NotBlank
    @Email(message = "Invalid email pattern")
    String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_+/\\-])[A-Za-z\\d@$!%*?&_+/\\-]{8,}$", message = "Invalid password pattern")
    @NotNull
    String password;

    @Pattern(regexp = "^[A-ZÀ-ÿ][a-zà-ÿ]{1,19}(?:[-' ][A-ZÀ-ÿ][a-zà-ÿ]{1,19})*$", message = "Invalid name pattern")
    @NotNull
    String name;

    @Pattern(regexp = "^[A-ZÀ-ÿ][a-zà-ÿ]{1,19}(?:[-' ][A-ZÀ-ÿ][a-zà-ÿ]{1,19})*$", message = "Invalid surname pattern")
    @NotNull
    String surname;

    @Pattern(regexp = "^[A-ZÀ-ÿ][a-zà-ÿ]{1,19}(?:[-' ][A-ZÀ-ÿ][a-zà-ÿ]{1,19})*$", message = "Invalid middle name pattern")
    String middleName;

    @Pattern(regexp = "^[A-Za-zÀ-ÿ0-9.,'’\\-:#/ ]{5,100}$", message = "Invalid address pattern")
    String address;

    @Pattern(regexp = "^\\+?\\d{1,4}?[ -]?\\(?\\d{2,4}\\)?[ -]?\\d{2,4}[ -]?\\d{2,4}[ -]?\\d{0,4}$", message = "Invalid phone number pattern")
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
    @JsonIgnore
    public UserDetails getUserDetails(){
        return new ShopsterUser(email, password, "CUSTOMER");
    }

    @JsonIgnore
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
