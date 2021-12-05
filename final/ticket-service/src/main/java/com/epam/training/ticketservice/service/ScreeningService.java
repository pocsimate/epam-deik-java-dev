package com.epam.training.ticketservice.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.transaction.Transactional;

import com.epam.training.ticketservice.exception.OverlappingScreeningException;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.google.common.collect.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service public class ScreeningService {

    private final int breakMinutes = 10;

    private MovieService movieService;
    private RoomService roomService;

    private ScreeningRepository screeningRepository;

    @Autowired
    public ScreeningService(MovieService movieService, RoomService roomService,
            ScreeningRepository screeningRepository) {
        this.movieService = movieService;
        this.roomService = roomService;
        this.screeningRepository = screeningRepository;
    }

    @Transactional
    public void createScreening(String movieTitle, String roomName, LocalDateTime screeningStartDate) {
        Movie movie = movieService.getMovieIfExists(movieTitle);
        Room room = roomService.getRoomIfExists(roomName);

        checkOverlap(room, movie, screeningStartDate);

        Screening screening = Screening.builder().movie(movie).room(room).screeningDate(screeningStartDate).build();

        screeningRepository.save(screening);
    }

    // FIXME nagyon gagyi, csak akkor biztos, ha egymás utáni adogatjuk be a filmeket időrendben
    public void checkOverlap(Room room, Movie movie, LocalDateTime screeningStartDate) {
        List<Screening> screeningList = screeningRepository.findScreeningsInRoomOnDay(room.getId(),
                screeningStartDate.with(LocalTime.MIN), screeningStartDate.with(LocalTime.MAX));
        if (!screeningList.isEmpty()) {
            Range<LocalDateTime> addableScreeningRange = Range.open(screeningStartDate,
                    screeningStartDate.plusMinutes(movie.getLength()));
            for (Screening screening : screeningList) {
                Range<LocalDateTime> screeningRangeWithBreak = Range.open(screening.getScreeningDate(),
                        screening.getScreeningDate().plusMinutes(screening.getMovie().getLength() + breakMinutes));
                if (screeningRangeWithBreak.isConnected(addableScreeningRange)) {
                    throwOverlapError(screeningRangeWithBreak, addableScreeningRange);
                }
            }
        }
    }

    public void throwOverlapError(Range<LocalDateTime> screeningRangeWithBreak,
            Range<LocalDateTime> addableScreeningRange) {
        LocalDateTime screeningRangeStart = screeningRangeWithBreak.lowerEndpoint();
        LocalDateTime screeningRangeEnd = screeningRangeWithBreak.upperEndpoint().minusMinutes(breakMinutes);
        Range<LocalDateTime> screeningRangeWithoutBreak = Range.open(screeningRangeStart, screeningRangeEnd);

        String errorMessage;

        if (screeningRangeWithoutBreak.isConnected(addableScreeningRange)) {
            errorMessage = "There is an overlapping screening";
        } else {
            errorMessage = "This would start in the break period after another screening in this room";
        }
        throw new OverlappingScreeningException(errorMessage);
    }

    public List<Screening> getScreeningsList() {
        return screeningRepository.findAll();
    }

    @Transactional
    public void deleteScreening(String movieTitle, String roomName, LocalDateTime screeningStartDate) {
        Movie movie = movieService.getMovieIfExists(movieTitle);
        Room room = roomService.getRoomIfExists(roomName);

        screeningRepository.deleteByParams(movie, room, screeningStartDate);
    }
}
