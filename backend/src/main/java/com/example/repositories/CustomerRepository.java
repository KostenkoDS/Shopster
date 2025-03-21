package com.example.repositories;

import com.example.entities.Customer;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Query("SELECT * FROM CUSTOMERS WHERE USER_ID = (SELECT ID FROM USERS WHERE EMAIL = :email)")
    Optional<Customer> findCustomerByUsername(@Param("email") String email);
    @Query("SELECT ID FROM CUSTOMERS WHERE USER_ID = (SELECT ID FROM USERS WHERE EMAIL = :email)")
    Optional<Long> findCustomerIdByEmail(@Param("email") String email);
    @Modifying
    @Query("DELETE FROM CUSTOMERS WHERE USER_ID = (SELECT ID FROM USERS WHERE EMAIL = :email)")
    void deleteCustomerByEmail(String email);
}
