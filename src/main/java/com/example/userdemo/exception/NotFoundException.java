package com.example.userdemo.exception;

/**
 * An exception indicating that a resource or entity was not found. This
 * exception is typically thrown when attempting to access or manipulate a
 * resource that does not exist.
 */
public class NotFoundException extends RuntimeException {
    /**
     * Constructs a new {@code NotFoundException} with the specified error message.
     *
     * @param message A descriptive message providing details about the error.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
