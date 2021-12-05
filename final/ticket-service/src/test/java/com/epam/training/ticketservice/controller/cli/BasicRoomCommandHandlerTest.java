package com.epam.training.ticketservice.controller.cli;

import java.util.Collections;
import java.util.List;

import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.service.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BasicRoomCommandHandlerTest {

    @Mock
    RoomService roomService;

    @InjectMocks
    BasicRoomCommandHandler roomCommandHandler;

    @Test
    void testListRoomsShouldReturnNoRoomsAtTheMomentWhenThereAreNoRooms() {
        // Given
        BDDMockito.when(roomService.getRoomList()).thenReturn(Collections.emptyList());
        String expectedResult = "There are no rooms at the moment";

        // When
        String result = roomCommandHandler.listRooms();

        // Then
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testListRoomsShouldReturnFormattedStringContainingAllRoomsWhenThereAreRooms() {
        // Given
        Room bigBen = Room.builder()
                .name("Big Ben")
                .numberOfColumns(10)
                .numberOfRows(10)
                .build();

        BDDMockito.when(roomService.getRoomList()).thenReturn(List.of(bigBen));
        String expectedResult = "Room " + bigBen.getName() + " with " + bigBen.getNumberOfRows() * bigBen.getNumberOfColumns()
                + " seats, " + bigBen.getNumberOfRows() + " rows and " + bigBen.getNumberOfColumns() + " columns";

        // When
        String result = roomCommandHandler.listRooms();

        // Then
        Assertions.assertEquals(expectedResult, result);
    }
}