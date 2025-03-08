package org.example.backend_user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer user_id) {
        super("User with id " + user_id + " not found");
    }
}
