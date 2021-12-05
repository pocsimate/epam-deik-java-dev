package com.epam.training.ticketservice.repository;

import java.util.Optional;

import com.epam.training.ticketservice.model.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-ci.properties")
class RoomRepositoryTest {

    @Autowired
    RoomRepository underTest;

    Room room;

    @Test
    public void testFindByNameShouldReturnTheRoomIfExists() {

        // Given
        room = new Room();
        room.setName("Test Room");
        room.setNumberOfRows(10);
        room.setNumberOfColumns(6);
        underTest.save(room);

        Optional<Room> dbRoom;

        // When
        dbRoom = underTest.findByName(room.getName());

        // Then
        Assertions.assertTrue(dbRoom.isPresent());
    }

    @Test
    public void testFindByNameShouldReturnEmptyOptionalIfNotExists() {

        // Given
        room = new Room();
        room.setName("Test Room2");
        room.setNumberOfRows(10);
        room.setNumberOfColumns(6);
        underTest.delete(room);

        Optional<Room> dbRoom;

        // When
        dbRoom = underTest.findByName(room.getName());

        // Then
        Assertions.assertTrue(dbRoom.isEmpty());
    }

    @Test
    public void testDeleteByNameShouldDeleteRoomIfExists() {

        // Given
        room = new Room();
        room.setName("Test Room2");
        room.setNumberOfRows(10);
        room.setNumberOfColumns(6);
        underTest.save(room);

        Optional<Room> dbRoom;

        // When
        underTest.deleteByName(room.getName());

        // Then
        dbRoom = underTest.findByName(room.getName());
        Assertions.assertTrue(dbRoom.isEmpty());
    }

}