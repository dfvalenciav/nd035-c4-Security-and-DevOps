package com.example.demo.security;

import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class
UserDetailsServiceImple implements UserDetailsService {

    private UserRepository userRepository;


    public UserDetailsServiceImple() {
    }

    public UserDetailsServiceImple(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
