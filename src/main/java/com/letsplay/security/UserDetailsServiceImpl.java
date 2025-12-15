package com.letsplay.security;

import com.letsplay.model.User;
import com.letsplay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * This class is a custom implementation of Spring Security's UserDetailsService interface.
 * It is responsible for loading user-specific data during the authentication process.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Spring will automatically inject an instance of UserRepository here.
     * This repository is used to fetch user data from the database.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * This method is called by Spring Security to load a user by their username (in our case, email).
     * @param email The username (email) of the user.
     * @return a UserDetails object containing the user's information and authorities.
     * @throws UsernameNotFoundException if the user with the given email is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Find the user by email in the database.
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Create a collection of GrantedAuthority objects based on the user's role.
        // Spring Security expects roles to start with "ROLE_", so we prefix it.
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));

        // Return a Spring Security User object with the user's email, password, and authorities.
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}