package com.epam.training.ticketservice.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.epam.training.ticketservice.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.exception.RoomDoesNotExistException;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    RoomRepository roomRepository;

    @InjectMocks
    RoomService roomService;

    Room testRoom;

    @BeforeEach
    void setUp() {
        testRoom = new Room();
        testRoom.setName("Test room");
        testRoom.setNumberOfColumns(10);
        testRoom.setNumberOfRows(4);
    }

    @Test
    void testFindRoomByNameShouldReturnRoomInOptionalWhenExists() {
        // Given
        BDDMockito.given(roomRepository.findByName(testRoom.getName())).willReturn(Optional.of(testRoom));

        // When
        Optional<Room> optionalRoom = roomService.findRoomByName(testRoom.getName());

        //Then
        Assertions.assertEquals(testRoom.getName(), optionalRoom.get().getName());
    }

    @Test
    void testGetRoomIfExistsShouldReturnRoomWhenExists() {
        // Given
        BDDMockito.given(roomRepository.findByName(testRoom.getName())).willReturn(Optional.of(testRoom));

        // When
        Room room = roomService.getRoomIfExists(testRoom.getName());

        //Then
        Assertions.assertTrue(Objects.nonNull(room));
    }

    @Test
    void testGetRoomIfExistsShouldThrowRoomDoesNotExistExceptionWhenNotExists() {
        // Given
        BDDMockito.given(roomRepository.findByName(testRoom.getName())).willReturn(Optional.empty());

        // When - Then
        Assertions.assertThrows(RoomDoesNotExistException.class, () -> roomService.getRoomIfExists(testRoom.getName()));
    }

    @Test
    void testCreateRoomShouldCreateRoomWhenNotExists() {
        // Given
        BDDMockito.given(roomRepository.findByName(testRoom.getName())).willReturn(Optional.empty());

        // When
        roomService.createRoom(testRoom);

        //Then
        Mockito.verify(roomRepository, Mockito.times(1)).save(testRoom);
    }

    @Test
    void testCreateRoomShouldThrowRoomAlreadyExistsExceptionWhenExists() {
        // Given
        BDDMockito.given(roomRepository.findByName(testRoom.getName())).willReturn(Optional.of(testRoom));

        // When - Then
        Assertions.assertThrows(RoomAlreadyExistsException.class, () -> roomService.createRoom(testRoom));
    }

    @Test
    void testUpdateRoomShouldUpdateRoomWhenExists() {
        // Given
        BDDMockito.given(roomRepository.findByName(testRoom.getName())).willReturn(Optional.of(testRoom));

        // When
        roomService.updateRoom(testRoom);

        //Then
        Mockito.verify(roomRepository, Mockito.times(1)).save(testRoom);
    }

    @Test
    void testUpdateRoomShouldThrowRoomDoesNotExistExceptionWhenNotExists() {
        // Given
        BDDMockito.given(roomRepository.findByName(testRoom.getName())).willReturn(Optional.empty());

        // When - Then
        Assertions.assertThrows(RoomDoesNotExistException.class, () -> roomService.updateRoom(testRoom));
    }

    @Test
    void testDeleteRoomShouldDeleteRoomWhenExists() {
        // Given
        BDDMockito.given(roomRepository.findByName(testRoom.getName())).willReturn(Optional.of(testRoom));

        // When
        roomService.deleteRoom(testRoom.getName());

        //Then
        Mockito.verify(roomRepository, Mockito.times(1)).deleteByName(testRoom.getName());
    }

    @Test
    void testDeleteRoomShouldThrowRoomDoesNotExistExceptionWhenNotExists() {
        // Given
        BDDMockito.given(roomRepository.findByName(testRoom.getName())).willReturn(Optional.empty());

        // When - Then
        Assertions.assertThrows(RoomDoesNotExistException.class, () -> roomService.deleteRoom(testRoom.getName()));
    }

    @Test
    void testGetRoomListShouldReturnListOfRooms() {
        // Given
        BDDMockito.given(roomRepository.findAll()).willReturn(Collections.emptyList());

        // When
        List<Room> roomList = roomService.getRoomList();

        //Then
        Assertions.assertTrue(roomList.isEmpty());
    }
}