package com.epam.training.ticketservice.exception;

public class RoomAlreadyExistsException extends RuntimeException {
    public RoomAlreadyExistsException(String room) {
        super("Room " + room + " already exists");
    }
}
