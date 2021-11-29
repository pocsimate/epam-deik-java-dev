package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public void createRoom(Room room){
        Optional<Room> optionalRoom = roomRepository.getRoomByName(room.getName());
        if (optionalRoom.isPresent()) {
            throw new RoomAlreadyExistsException(room.getName());
        }
        roomRepository.save(room);
    }
}
