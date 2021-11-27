package com.epam.training.ticketservice.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username){
        super("User " + username + " already exists");
    }
}
