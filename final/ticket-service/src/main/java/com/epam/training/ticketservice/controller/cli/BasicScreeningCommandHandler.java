package com.epam.training.ticketservice.controller.cli;

import java.util.List;
import java.util.StringJoiner;

import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class BasicScreeningCommandHandler {

    ScreeningService screeningService;

    @Autowired
    public BasicScreeningCommandHandler(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @ShellMethod(value = "List the screenings", key = "list screenings")
    public String listScreenings() {
        List<Screening> screeningList = screeningService.getScreeningsList();

        if (screeningList.isEmpty()) {
            return "There are no screenings";
        }

        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        screeningList.forEach((room -> stringJoiner.add(room.toString())));

        return stringJoiner.toString();
    }
}
