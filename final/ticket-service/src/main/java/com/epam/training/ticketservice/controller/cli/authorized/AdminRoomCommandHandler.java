package com.epam.training.ticketservice.controller.cli.authorized;

import com.epam.training.ticketservice.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.exception.RoomDoesNotExistException;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class AdminRoomCommandHandler extends AuthorizedCommand {

    private final RoomService roomService;

    @Autowired
    public AdminRoomCommandHandler(RoomService roomService) {
        this.roomService = roomService;
    }

    @ShellMethodAvailability("isAdminAuthorized")
    @ShellMethod(value = "Create new room", key = "create room")
    public String createRoom(String name, int numberOfRows, int numberOfColumns){
        try{
            Room room = Room.builder()
                    .name(name)
                    .numberOfRows(numberOfRows)
                    .numberOfColumns(numberOfColumns)
                    .build();
            roomService.createRoom(room);
            return "Room created successfully";
        } catch (RoomAlreadyExistsException ex) {
            return ex.getMessage();
        }
    }

    @ShellMethodAvailability("isAdminAuthorized")
    @ShellMethod(value = "Edit existing room", key = "update room")
    public String editRoom(String name, int numberOfRows, int numberOfColumns) {
        try {
            Room room = Room.builder()
                    .name(name)
                    .numberOfRows(numberOfRows)
                    .numberOfColumns(numberOfColumns)
                    .build();
            roomService.updateRoom(room);
            return "Room updated successfully";
        } catch (RoomDoesNotExistException ex) {
            return ex.getMessage();
        }
    }

    @ShellMethodAvailability("isAdminAuthorized")
    @ShellMethod(value = "Delete existing room", key = "delete room")
    public String deleteRoom(String name) {
        try {
            roomService.deleteRoom(name);
            return "Room deleted successfully";
        } catch (RoomDoesNotExistException ex) {
            return ex.getMessage();
        }
    }
}
