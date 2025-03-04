package com.example.auth;

import com.example.shopster.ShopsterApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ShopsterApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
class ShopsterUserDetailsServiceTest {

    @Autowired
    ShopsterUserDetailsService service;

    @Test
    void createModifyAndDeleteUser(){
        String userName = "d@gmail.com";
        String password = "12345";
        String role = "CUSTOMER";
        ShopsterUser user = new ShopsterUser(userName, password, role);
        assertFalse(service.userExists(userName));
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(userName));

        service.createUser(user);
        assertTrue(service.userExists(userName));
        ShopsterUser foundUser = (ShopsterUser) service.loadUserByUsername(userName);
        var auths = foundUser.getAuthorities();
        var authRole = new SimpleGrantedAuthority(role);
        assertAll(
                () -> assertEquals(userName, foundUser.getUsername()),
                () -> assertEquals(password, foundUser.getPassword()),
                () -> assertTrue(foundUser.getAuthorities().contains(new SimpleGrantedAuthority(role)))
        );

        String newPassword = "qwerty";
        String newRole = "MANAGER";
        ShopsterUser toUpdateUser = new ShopsterUser(userName, newPassword, newRole);
        toUpdateUser.getUserRecord().setId(foundUser.getUserRecord().getId());
        service.updateUser(toUpdateUser);
        var updatedUser = service.loadUserByUsername(userName);
        assertAll(
                () -> assertEquals(userName, updatedUser.getUsername()),
                () -> assertEquals(newPassword, updatedUser.getPassword()),
                () -> assertTrue(updatedUser.getAuthorities().contains(new SimpleGrantedAuthority(newRole)))
        );

        service.deleteUser(userName);
        assertFalse(service.userExists(userName));
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(userName));
    }

}