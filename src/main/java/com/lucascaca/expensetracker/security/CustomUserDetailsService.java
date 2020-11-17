package com.lucascaca.expensetracker.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.lucascaca.expensetracker.domain.User;
import com.lucascaca.expensetracker.repositories.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository users;

    public CustomUserDetailsService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.users.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Email: " + email + " not found"));
        
        return user;
    }
}
