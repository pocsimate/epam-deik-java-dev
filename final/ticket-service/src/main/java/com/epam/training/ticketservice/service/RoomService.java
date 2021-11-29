package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.exception.RoomDoesNotExistException;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public Optional<Room> getRoomByName(String name) {
        return roomRepository.getRoomByName(name);
    }

    @Transactional
    public void createRoom(Room room){
        Optional<Room> optionalRoom = getRoomByName(room.getName());
        if (optionalRoom.isPresent()) {
            throw new RoomAlreadyExistsException(room.getName());
        }
        roomRepository.save(room);
    }

    @Transactional
    public void updateRoom(Room room) {
        Optional<Room> optionalRoom = getRoomByName(room.getName());
        if (optionalRoom.isEmpty()) {
            throw new RoomDoesNotExistException(room.getName());
        } else {
            Room dbRoom = optionalRoom.get();
            dbRoom.setName(room.getName());
            dbRoom.setNumberOfColumns(room.getNumberOfColumns());
            dbRoom.setNumberOfRows(room.getNumberOfRows());
        }
    }

    @Transactional
    public void deleteRoom(String name) {
        Optional<Room> optionalRoom = getRoomByName(name);
        if (optionalRoom.isEmpty()) {
            throw new RoomDoesNotExistException(name);
        } else {
            roomRepository.deleteByTitle(name);
        }
    }
}
