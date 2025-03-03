package com.example.auth;

import com.example.entities.UserRecord;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class ShopsterUserDetailsService implements UserDetailsManager {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRecord userRecord = repository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with such email: "+username));
        return new ShopsterUser(userRecord);
    }

    @Override
    public void createUser(UserDetails user) {
        if(!(user instanceof ShopsterUser shopsterUser))
            throw new RuntimeException("Wrong type of UserDetails");
        repository.save(shopsterUser.getUserRecord());
    }

    @Override
    public void updateUser(UserDetails user) {
        if(!(user instanceof ShopsterUser shopsterUser))
            throw new RuntimeException("Wrong type of UserDetails");
        repository.save(shopsterUser.getUserRecord());
    }

    @Override
    public void deleteUser(String username) {
        repository.deleteByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }
}
