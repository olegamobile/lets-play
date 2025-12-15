package com.letsplay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This class is a global exception handler for the application.
 * @ControllerAdvice allows us to consolidate our exception handling logic in one place.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * This method handles all exceptions of type Exception.class.
     * You can create more specific exception handlers for different types of exceptions.
     * For example, you could create a UserNotFoundException and a specific handler for it.
     * @param e The exception that was thrown.
     * @return a ResponseEntity with an appropriate HTTP status code and error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // Log the exception for debugging purposes.
        e.printStackTrace();
        // Return a generic error message to the client.
        return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
