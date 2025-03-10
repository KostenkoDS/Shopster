package com.example.auth;

import com.example.entities.UserRecord;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class ShopsterUserDetailsService implements UserDetailsManager {

    @Autowired
    UserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRecord userRecord = repository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with such email: "+username));
        return new ShopsterUser(userRecord);
    }

    public Long getUserIdByUsername(String username){
        return repository.findIdByUsername(username);
    }

    @Override
    public void createUser(UserDetails user) {
        if(!(user instanceof ShopsterUser shopsterUser))
            throw new RuntimeException("Wrong type of UserDetails");
        if(userExists(user.getUsername()))
            throw new UserAlreadyExistsException("Cannot not create a new user that already exists");
        shopsterUser.getUserRecord().setPassword(passwordEncoder.encode(user.getPassword()));
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
        throw new RuntimeException("Changing passwords is not yet implemented");
    }

    @Override
    public boolean userExists(String username) {
        return repository.findUserByEmail(username).isPresent();
    }
}
