package com.epam.training.ticketservice.controller.cli.secured;

import com.epam.training.ticketservice.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AdminRoomCommandHandler {

    @Autowired
    RoomService roomService;

    @ShellMethod(value = "Create new room", key = "create room")
    public String createRoom(String name, int numberOfRows, int numberOfColumns){
        try{
            Room room = Room.builder()
                    .name(name)
                    .numberOfRows(numberOfRows)
                    .numberOfColumns(numberOfColumns)
                    .build();
            roomService.createRoom(room);
            return "Movie created successfully";
        } catch (RoomAlreadyExistsException ex) {
            return ex.getMessage();
        }
    }
}
