package com.example.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShopsterAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private ShopsterUserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        UserDetails user = userDetailsService.loadUserByUsername(userName);
        boolean userNameMatches = user.getUsername().equals(userName);
        boolean passwordMatches = passwordEncoder.matches(rawPassword, user.getPassword());

        if(userNameMatches && passwordMatches){
            return new UsernamePasswordAuthenticationToken(userName, null, user.getAuthorities());
        }
        else{
            throw new BadCredentialsException("U wrong");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken
                .class
                .isAssignableFrom(authentication);
    }
}
