package com.eventApp.ExceptionHandler;

/**
 * Custom exception class for handling database-related errors within the Event App.
 * Extends the standard Exception class to provide meaningful error messages.
 */
public class DatabaseExceptionHandler extends Exception {
    /**
     * Constructs a new DatabaseExceptionHandler with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public DatabaseExceptionHandler(String message) {
        super(message);
    }
}
