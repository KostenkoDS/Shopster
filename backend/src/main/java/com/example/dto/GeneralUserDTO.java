package com.example.dto;

import com.example.entities.Customer;

public class GeneralUserDTO {
    String email;
    String name;
    String surname;
    String middleName;
    String address;
    String phoneNumber;

    public GeneralUserDTO(String email, String name, String surname, String middleName, String address, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public GeneralUserDTO(Customer customer, String email){
        this.email = email;
        this.name = customer.getName();
        this.surname = customer.getSurname();
        this.middleName = customer.getMiddleName();
        this.address = customer.getAddress();
        this.phoneNumber = customer.getPhoneNumber();
    }

    public String getEmail() {
        return email;
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
