package com.letsplay.service;

import com.letsplay.model.User;
import com.letsplay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This class is a Spring service that contains the business logic for User operations.
 * @Service annotation marks this class as a Spring service, which means it will be managed by the Spring container.
 */
@Service
public class UserService {

    /**
     * @Autowired annotation is used for automatic dependency injection.
     * Spring will automatically inject an instance of UserRepository here.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Spring will automatically inject an instance of PasswordEncoder here.
     * We configured this bean in the SecurityConfig class.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * This method creates a new user.
     * It first encodes the user's password and then saves the user to the database.
     * @param user The User object to be created.
     * @return The saved User object.
     */
    public User createUser(User user) {
        // Encode the user's password before saving it to the database.
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Save the user to the database using the UserRepository.
        return userRepository.save(user);
    }

    // You can add other service methods here, for example:
    // - A method to get a user by their ID.
    // - A method to get all users.
    // - A method to update a user.
    // - A method to delete a user.
}
