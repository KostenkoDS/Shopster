package com.example.repositories;

import com.example.entities.UserRecord;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserRecord, Long> {
    Optional<UserRecord> findUserByEmail(String email);
    @Modifying
    @Query("DELETE FROM USERS WHERE EMAIL = :username")
    void deleteByUsername(@Param("username") String username);
}
