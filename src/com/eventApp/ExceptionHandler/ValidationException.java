package com.eventApp.ExceptionHandler;

/**
 * Custom exception class for handling validation errors within the Event App.
 * Extends the standard Exception class to provide specific validation error messages.
 */
public class ValidationException extends Exception {
    /**
     * Constructs a new ValidationException with the specified detail message.
     *
     * @param message the detail message explaining the validation error
     */
    public ValidationException(String message) {
        super(message);
    }
}
