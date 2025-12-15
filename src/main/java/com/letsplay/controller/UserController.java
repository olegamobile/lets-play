package com.letsplay.controller;

import com.letsplay.dto.UserDto;
import com.letsplay.model.User;
import com.letsplay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is a Spring REST controller that handles HTTP requests related to Users.
 * @RestController is a specialized version of the controller. It includes the @Controller and @ResponseBody annotations.
 * @RequestMapping("/api/users") maps all requests starting with "/api/users" to this controller.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    /**
     * Spring will automatically inject an instance of UserService here.
     */
    @Autowired
    private UserService userService;

    /**
     * This method handles POST requests to "/api/users".
     * It creates a new user.
     * The @RequestBody annotation tells Spring to deserialize the incoming JSON into a User object.
     * @param user The User object to be created.
     * @return a UserDto object, which is a safe representation of the User.
     */
    @PostMapping
    public UserDto createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return convertToDto(createdUser);
    }

    /**
     * This is a helper method to convert a User object to a UserDto object.
     * This is important to avoid exposing sensitive information like the password.
     * @param user The User object to be converted.
     * @return a UserDto object.
     */
    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return userDto;
    }

    // You can add other controller methods here for getting, updating, and deleting users.
}
