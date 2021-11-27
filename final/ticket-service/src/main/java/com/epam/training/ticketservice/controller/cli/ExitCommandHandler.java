package com.epam.training.ticketservice.controller.cli;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Quit;

@ShellComponent
public class ExitCommandHandler implements Quit.Command{

    @ShellMethod(value = "Exits the application", key = {"exit", "quit", "terminate"})
    public void exit() {
        System.out.println("bye-bye");
        System.exit(0);
    }
}
