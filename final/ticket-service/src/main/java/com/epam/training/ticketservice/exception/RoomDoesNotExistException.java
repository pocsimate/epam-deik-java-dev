package com.epam.training.ticketservice.exception;

public class RoomDoesNotExistException extends RuntimeException {
    public RoomDoesNotExistException(String room){
        super("Room " + room + " does not exists");
    }
}
