package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.exception.RoomDoesNotExistException;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Optional<Room> findRoomByName(String name) {
        return roomRepository.findByName(name);
    }

    public Room getRoomIfExists(String name) {
        Optional<Room> room = findRoomByName(name);
        if (room.isPresent()) {
            return room.get();
        } else {
            throw new RoomDoesNotExistException(name);
        }
    }

    @Transactional
    public void createRoom(Room room){
        Optional<Room> optionalRoom = findRoomByName(room.getName());
        if (optionalRoom.isPresent()) {
            throw new RoomAlreadyExistsException(room.getName());
        }
        roomRepository.save(room);
    }

    @Transactional public void updateRoom(Room room) {
        Room dbRoom = getRoomIfExists(room.getName());
        dbRoom.setName(room.getName());
        dbRoom.setNumberOfColumns(room.getNumberOfColumns());
        dbRoom.setNumberOfRows(room.getNumberOfRows());
    }

    @Transactional
    public void deleteRoom(String name) {
        Room dbRoom = getRoomIfExists(name);
        roomRepository.deleteByName(dbRoom.getName());
    }

    @Transactional
    public List<Room> getRoomList() {
        return roomRepository.findAll();
    }
}
