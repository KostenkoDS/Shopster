package com.example.repositories;

import com.example.entities.Manager;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ManagerRepository extends CrudRepository<Manager, Long> {
    @Query("SELECT * FROM MANAGERS WHERE USER_ID = (SELECT ID FROM USERS WHERE EMAIL = :email)")
    Optional<Manager> findManagerByUsername(@Param("email") String email);
}
