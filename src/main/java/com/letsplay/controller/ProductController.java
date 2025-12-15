package com.letsplay.controller;

import com.letsplay.model.Product;
import com.letsplay.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class is a Spring REST controller that handles HTTP requests related to Products.
 * @RestController is a specialized version of the controller. It includes the @Controller and @ResponseBody annotations.
 * @RequestMapping("/api/products") maps all requests starting with "/api/products" to this controller.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    /**
     * Spring will automatically inject an instance of ProductRepository here.
     */
    @Autowired
    private ProductRepository productRepository;

    /**
     * This method handles GET requests to "/api/products".
     * It returns a list of all products.
     * @return a List of Product objects.
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // You can add other controller methods here for creating, updating, and deleting products.
    // For example:
    // - @PostMapping to create a new product.
    // - @PutMapping("/{id}") to update an existing product.
    // - @DeleteMapping("/{id}") to delete a product.
    // - @GetMapping("/{id}") to get a single product by its ID.
}
