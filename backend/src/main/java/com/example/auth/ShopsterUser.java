package com.example.auth;

import com.example.entities.UserRecord;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ShopsterUser implements UserDetails {

    public ShopsterUser(UserRecord userRecord) {
        this.userRecord = userRecord;
    }

    private final UserRecord userRecord;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRecord.getRole().name()));
    }

    @Override
    public String getPassword() {
        return userRecord.getPassword();
    }

    @Override
    public String getUsername() {
        return userRecord.getEmail();
    }

    public UserRecord getUserRecord() {
        return userRecord;
    }
}
