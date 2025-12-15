package com.letsplay.repository;

import com.letsplay.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * This interface is a Spring Data repository for the Product entity.
 * MongoRepository provides all the necessary methods to perform CRUD (Create, Read, Update, Delete) operations.
 * We are extending MongoRepository<Product, String> where Product is the domain class and String is the type of the ID.
 */
public interface ProductRepository extends MongoRepository<Product, String> {
    // We can add custom query methods here if needed in the future.
}
