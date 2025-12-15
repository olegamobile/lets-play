package com.letsplay.repository;

import com.letsplay.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * This interface is a Spring Data repository for the User entity.
 * MongoRepository provides all the necessary methods to perform CRUD (Create, Read, Update, Delete) operations.
 * We are extending MongoRepository<User, String> where User is the domain class and String is the type of the ID.
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * This method will find a User by their email.
     * Spring Data MongoDB will automatically implement this method based on its name.
     * It returns an Optional<User> which is a container that may or may not contain a non-null User object.
     * This is a good way to handle cases where a user might not be found.
     *
     * @param email The email of the user to find.
     * @return an Optional containing the user if found, or an empty Optional otherwise.
     */
    Optional<User> findByEmail(String email);
}
