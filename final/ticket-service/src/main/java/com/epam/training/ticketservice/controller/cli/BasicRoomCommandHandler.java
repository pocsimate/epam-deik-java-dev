package com.epam.training.ticketservice.controller.cli;

import java.util.List;
import java.util.StringJoiner;

import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class BasicRoomCommandHandler {

    private RoomService roomService;

    @Autowired
    public BasicRoomCommandHandler(RoomService roomService) {
        this.roomService = roomService;
    }

    @ShellMethod(value = "List the rooms", key = "list rooms")
    public String listRooms() {
        List<Room> roomList = roomService.getRoomList();

        if (roomList.isEmpty()) {
            return "There are no rooms at the moment";
        }

        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        roomList.forEach((room -> stringJoiner.add(room.toString())));

        return stringJoiner.toString();
    }
}
