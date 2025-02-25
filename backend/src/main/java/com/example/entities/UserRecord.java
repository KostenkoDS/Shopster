package com.example.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

@Table("USERS")
public class UserRecord {
    @Id
    Long id;
    String email;
    String password;
    Role role;

    public enum Role {
        CUSTOMER, MANAGER;
    }
}
