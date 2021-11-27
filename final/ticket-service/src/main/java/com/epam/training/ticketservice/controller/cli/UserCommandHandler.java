package com.epam.training.ticketservice.controller.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UserCommandHandler {

    @Autowired
    AuthenticationManager authenticationManager;

    @ShellMethod(value = "Login user", key = "sign in")
    public String login(String username, String password){
        Authentication request = new UsernamePasswordAuthenticationToken(username, password);

        try {
            Authentication result = authenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            return "Authentication success";
        } catch (AuthenticationException e) {
            return e.getMessage();
        }
    }
}
