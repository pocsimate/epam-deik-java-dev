package com.epam.training.ticketservice.controller.cli.authorized;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.epam.training.ticketservice.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class AdminScreeningCommandHandler extends AuthorizedCommand {

    private ScreeningService screeningService;

    @Autowired
    public AdminScreeningCommandHandler(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @ShellMethodAvailability("isAdminAuthorized")
    @ShellMethod(value = "Create new screening", key = "create screening")
    private String createScreening(String movieTitle, String roomName, String screeningDate) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime screeningStartDate = LocalDateTime.parse(screeningDate, dateFormat);
        try {
            screeningService.createScreening(movieTitle, roomName, screeningStartDate);
        } catch (RuntimeException ex) {
            return ex.getMessage();
        }
        return "Screening created successfully";
    }

    @ShellMethodAvailability("isAdminAuthorized")
    @ShellMethod(value = "Delete existing screening", key = "delete screening")
    private String deleteScreening(String movieTitle, String roomName, String screeningDate) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime screeningStartDate = LocalDateTime.parse(screeningDate, dateFormat);
        try {
            screeningService.deleteScreening(movieTitle, roomName, screeningStartDate);
        } catch (RuntimeException ex) {
            return ex.getMessage();
        }
        return "Screening deleted successfully";
    }
}
