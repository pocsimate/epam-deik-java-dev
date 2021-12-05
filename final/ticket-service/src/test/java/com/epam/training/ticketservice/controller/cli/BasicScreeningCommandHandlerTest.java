package com.epam.training.ticketservice.controller.cli;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.service.ScreeningService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BasicScreeningCommandHandlerTest {

    @Mock
    ScreeningService screeningService;

    @InjectMocks
    BasicScreeningCommandHandler screeningCommandHandler;

    @Test
    void testListScreeningsShouldReturnNoScreeningsAtTheMomentWhenThereAreNoScreenings() {
        // Given
        BDDMockito.when(screeningService.getScreeningsList()).thenReturn(Collections.emptyList());
        String expectedResult = "There are no screenings";

        // When
        String result = screeningCommandHandler.listScreenings();

        // Then
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testListScreeningsShouldReturnFormattedStringOfScreeningsWhenThereAreScreenings() {
        // Given
        Screening screening = Screening.builder()
                .room(Room.builder().name("Test Room").numberOfRows(10).numberOfColumns(10).build())
                .movie(Movie.builder().title("Test Movie").category("Test category").length(60).build())
                .screeningDate(LocalDateTime.of(2021,12,28,16,30))
                .build();

        BDDMockito.when(screeningService.getScreeningsList()).thenReturn(List.of(screening));
        String expectedResult = screening.getMovie().getTitle() + " (" + screening.getMovie().getCategory() + ", " + screening.getMovie().getLength() + " minutes), screened in room "
                + screening.getRoom().getName() + ", at " + screening.getScreeningDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // When
        String result = screeningCommandHandler.listScreenings();

        // Then
        Assertions.assertEquals(expectedResult, result);
    }
}