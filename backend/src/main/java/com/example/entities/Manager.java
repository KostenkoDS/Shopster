package com.example.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("MANAGERS")
public class Manager {
    @Id
    Long id;
    Long userId;
    String name;
    String surname;
    String middleName;
    String address;
    String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return Objects.equals(getId(), manager.getId()) && Objects.equals(getUserId(), manager.getUserId()) && Objects.equals(getName(), manager.getName()) && Objects.equals(getSurname(), manager.getSurname()) && Objects.equals(getMiddleName(), manager.getMiddleName()) && Objects.equals(getAddress(), manager.getAddress()) && Objects.equals(getPhoneNumber(), manager.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getName(), getSurname(), getMiddleName(), getAddress(), getPhoneNumber());
    }
}
